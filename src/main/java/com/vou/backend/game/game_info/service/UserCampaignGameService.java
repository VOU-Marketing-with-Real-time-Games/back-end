package com.vou.backend.game.game_info.service;

import com.vou.backend.game.game_info.exception.GameCampaignNotFoundException;
import com.vou.backend.game.game_info.exception.UserCampaignGameNotFoundException;
import com.vou.backend.game.game_info.model.GameCampaign;
import com.vou.backend.game.game_info.model.UserCampaignGame;
import com.vou.backend.game.game_info.repository.UserCampaignGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCampaignGameService {
    private final UserCampaignGameRepository userCampaignGameRepository;
    private final GameCampaignService gameCampaignService;

    public UserCampaignGame saveUserCampaignGame(UserCampaignGame userCampaignGame) throws GameCampaignNotFoundException {
        GameCampaign gameCampaign = gameCampaignService.findById(userCampaignGame.getCampaignGame().getId());
        if(existsByUserIdAndCampaignGameId(userCampaignGame.getUserId(), gameCampaign.getId())) {
            return userCampaignGameRepository.findByUserIdAndCampaignGameId(userCampaignGame.getUserId(), userCampaignGame.getCampaignGame().getId());
        }
        userCampaignGame.setCampaignGame(gameCampaign);
        userCampaignGame.setIsCompleted(false);
        return userCampaignGameRepository.save(userCampaignGame);
    }

    private boolean existsByUserIdAndCampaignGameId(Long userId, Long campaignGameId) {
        return userCampaignGameRepository.findByUserIdAndCampaignGameId(userId, campaignGameId) != null;
    }

    public UserCampaignGame getUserCampaignGameById(Long id) throws UserCampaignGameNotFoundException {
        return userCampaignGameRepository.findById(id)
                .orElseThrow(() -> new UserCampaignGameNotFoundException("UserCampaignGame not found with id: " + id));
    }

    public List<UserCampaignGame> getAllUserCampaignGames() {
        return userCampaignGameRepository.findAll();
    }

    // Only update completed status
    public UserCampaignGame updateUserCampaignGame(Long id) throws UserCampaignGameNotFoundException {
        UserCampaignGame userCampaignGame = userCampaignGameRepository.findById(id)
                .orElseThrow(() -> new UserCampaignGameNotFoundException("UserCampaignGame not found with id: " + id));
        userCampaignGame.setIsCompleted(true);
        return userCampaignGameRepository.save(userCampaignGame);
    }

    public List<UserCampaignGame> findByUserId(Long userId) {
        return userCampaignGameRepository.findByUserId(userId);
    }
}
