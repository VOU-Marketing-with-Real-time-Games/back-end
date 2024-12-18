package com.vou.backend.game.quizz.service;

import com.vou.backend.game.game_info.exception.GameCampaignNotFoundException;
import com.vou.backend.game.game_info.exception.Game_CampaignGameConflict;
import com.vou.backend.game.game_info.exception.QuizzNotFoundException;
import com.vou.backend.game.game_info.model.GameCampaign;
import com.vou.backend.game.game_info.model.GameType;
import com.vou.backend.game.game_info.service.GameCampaignService;
import com.vou.backend.game.quizz.model.Quizz;
import com.vou.backend.game.quizz.repository.QuizzRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
public class QuizzService {

    private final QuizzRepository quizzRepository;
    private final GameCampaignService gameCampaignService;

    public List<Quizz> getAllQuizzes() {
        return quizzRepository.findAll();
    }

    public Quizz getQuizzById(Long id) throws QuizzNotFoundException {
        return quizzRepository.findById(id).orElseThrow(() -> new QuizzNotFoundException("Quizz not found with id " + id));
    }

    public Quizz createQuizz(Quizz quizz) throws Game_CampaignGameConflict, GameCampaignNotFoundException {
        GameCampaign gameCampaign = gameCampaignService.findById(quizz.getCampaignGameId());
        if(gameCampaign.getGameInfo().getType() != GameType.QUIZZ)
        {
            throw new Game_CampaignGameConflict("Game Campaign does not match with the game type");
        }

        quizz.setCreatedAt(new Date());
        Quizz savedQuizz = quizzRepository.save(quizz);

        gameCampaign.setGameId(savedQuizz.getId());
        gameCampaignService.updateGameCampaign(gameCampaign);

        return savedQuizz;
    }

    public Quizz updateQuizz(Long id, Quizz quizzDetails) throws QuizzNotFoundException {
        Quizz quizz = getQuizzById(id);
        quizz.copy(quizzDetails);
        return quizzRepository.save(quizz);
    }

    public void deleteQuizz(Long id) throws QuizzNotFoundException, GameCampaignNotFoundException {
        Quizz quizz = getQuizzById(id);
        GameCampaign gameCampaign = gameCampaignService.findById(quizz.getCampaignGameId());
        gameCampaign.setGameId(null);
        quizzRepository.deleteById(id);
    }
}
