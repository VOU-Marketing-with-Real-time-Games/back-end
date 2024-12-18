package com.vou.backend.game.game_info.service;

import com.vou.backend.game.game_info.dto.UserCampaignGameRequestDto;
import com.vou.backend.game.game_info.dto.UserCampaignGameResponseDto;
import com.vou.backend.game.game_info.exception.GameCampaignNotFoundException;
import com.vou.backend.game.game_info.exception.UserCampaignGameNotFoundException;
import com.vou.backend.game.game_info.model.GameCampaign;
import com.vou.backend.game.game_info.model.UserCampaignGame;
import com.vou.backend.game.game_info.repository.UserCampaignGameRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCampaignGameService {
    private final UserCampaignGameRepository userCampaignGameRepository;
    private final GameCampaignService gameCampaignService;
    private final ModelMapper modelMapper;

    public UserCampaignGameResponseDto saveUserCampaignGame(UserCampaignGameRequestDto userCampaignGameDto) throws GameCampaignNotFoundException {
        UserCampaignGame userCampaignGame = modelMapper.map(userCampaignGameDto, UserCampaignGame.class);

        GameCampaign gameCampaign = gameCampaignService.getById(userCampaignGame.getCampaignGame().getId());
        if(existsByUserIdAndCampaignGameId(userCampaignGame.getUserId(), gameCampaign.getId())) {
            UserCampaignGame existingUserCampaignGame =  userCampaignGameRepository.findByUserIdAndCampaignGameId(userCampaignGame.getUserId(), userCampaignGame.getCampaignGame().getId());
            return modelMapper.map(existingUserCampaignGame, UserCampaignGameResponseDto.class);
        }
        userCampaignGame.setCampaignGame(gameCampaign);
        userCampaignGame.setIsCompleted(false);
        UserCampaignGame savedUserCampaignGame = userCampaignGameRepository.save(userCampaignGame);
        return modelMapper.map(savedUserCampaignGame, UserCampaignGameResponseDto.class);
    }

    private boolean existsByUserIdAndCampaignGameId(Long userId, Long campaignGameId) {
        return userCampaignGameRepository.findByUserIdAndCampaignGameId(userId, campaignGameId) != null;
    }

    public UserCampaignGameResponseDto getUserCampaignGameById(Long id) throws UserCampaignGameNotFoundException {
        UserCampaignGame userCampaignGame = userCampaignGameRepository.findById(id)
                .orElseThrow(() -> new UserCampaignGameNotFoundException("UserCampaignGame not found with id: " + id));
        return  modelMapper.map(userCampaignGame, UserCampaignGameResponseDto.class);
    }

    public List<UserCampaignGameResponseDto> getAllUserCampaignGames() {
        List<UserCampaignGame> userCampaignGames = userCampaignGameRepository.findAll();
        return userCampaignGames.stream()
                .map(userCampaignGame -> modelMapper.map(userCampaignGame, UserCampaignGameResponseDto.class))
                .collect(Collectors.toList());
    }

    // Only update completed status
    public UserCampaignGameResponseDto updateUserCampaignGame(Long id) throws UserCampaignGameNotFoundException {
        UserCampaignGame userCampaignGame = userCampaignGameRepository.findById(id)
                .orElseThrow(() -> new UserCampaignGameNotFoundException("UserCampaignGame not found with id: " + id));
        userCampaignGame.setIsCompleted(true);
        UserCampaignGame savedUserCampaignGame =  userCampaignGameRepository.save(userCampaignGame);
        return modelMapper.map(savedUserCampaignGame, UserCampaignGameResponseDto.class);
    }

    public List<UserCampaignGameResponseDto> findByUserId(Long userId) {
        List<UserCampaignGame> userCampaignGames = userCampaignGameRepository.findByUserId(userId);
        return userCampaignGames.stream()
                .map(userCampaignGame -> modelMapper.map(userCampaignGame, UserCampaignGameResponseDto.class))
                .collect(Collectors.toList());
    }
}
