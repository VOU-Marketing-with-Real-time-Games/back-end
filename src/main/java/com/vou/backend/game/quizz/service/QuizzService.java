package com.vou.backend.game.quizz.service;

import com.vou.backend.game.game_info.exception.GameCampaignNotFoundException;
import com.vou.backend.game.game_info.exception.Game_CampaignGameConflict;
import com.vou.backend.game.game_info.exception.QuizzNotFoundException;
import com.vou.backend.game.game_info.model.GameCampaign;
import com.vou.backend.game.game_info.model.GameType;
import com.vou.backend.game.game_info.service.GameCampaignService;
import com.vou.backend.game.quizz.dto.QuizzRequestDto;
import com.vou.backend.game.quizz.dto.QuizzResponseDto;
import com.vou.backend.game.quizz.model.Quizz;
import com.vou.backend.game.quizz.repository.QuizzRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizzService {

    private final QuizzRepository quizzRepository;
    private final GameCampaignService gameCampaignService;
    private final ModelMapper modelMapper;

    public List<QuizzResponseDto> getAllQuizzes() {
        return quizzRepository.findAll().stream()
                .map(quizz -> modelMapper.map(quizz, QuizzResponseDto.class))
                .collect(Collectors.toList());
    }

    public QuizzResponseDto getQuizzById(Long id) throws QuizzNotFoundException {
        Quizz quizz =  quizzRepository.findById(id).orElseThrow(() -> new QuizzNotFoundException("Quizz not found with id " + id));
        return modelMapper.map(quizz, QuizzResponseDto.class);
    }

    public Quizz findQuizzById(Long id) throws QuizzNotFoundException {
        return quizzRepository.findById(id).orElseThrow(() -> new QuizzNotFoundException("Quizz not found with id " + id));
    }


    public QuizzResponseDto createQuizz(QuizzRequestDto quizzRequestDto) throws Game_CampaignGameConflict, GameCampaignNotFoundException {
        Quizz quizz = modelMapper.map(quizzRequestDto, Quizz.class);

        GameCampaign gameCampaign = gameCampaignService.getById(quizz.getCampaignGameId());
        if(gameCampaign.getGameInfo().getType() != GameType.QUIZZ)
        {
            throw new Game_CampaignGameConflict("Game Campaign does not match with the game type");
        }

        quizz.setCreatedAt(new Date());
        Quizz savedQuizz = quizzRepository.save(quizz);

        gameCampaign.setGameId(savedQuizz.getId());
        gameCampaignService.updateGameCampaign(gameCampaign);

        return modelMapper.map(savedQuizz, QuizzResponseDto.class);
    }

    public QuizzResponseDto updateQuizz(Long id, QuizzRequestDto quizzRequestDto) throws QuizzNotFoundException {
        Quizz quizzDetails = modelMapper.map(quizzRequestDto, Quizz.class);
        Quizz quizz = findQuizzById(id);
        quizz.copy(quizzDetails);
        Quizz updatedQuizz = quizzRepository.save(quizz);
        return modelMapper.map(updatedQuizz, QuizzResponseDto.class);
    }

    public void deleteQuizz(Long id) throws QuizzNotFoundException, GameCampaignNotFoundException {
        Quizz quizz = findQuizzById(id);
        GameCampaign gameCampaign = gameCampaignService.getById(quizz.getCampaignGameId());
        gameCampaign.setGameId(null);
        quizzRepository.deleteById(id);
    }
}
