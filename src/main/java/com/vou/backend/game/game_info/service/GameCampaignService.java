package com.vou.backend.game.game_info.service;

import com.vou.backend.game.game_info.exception.GameCampaignNotFoundException;
import com.vou.backend.game.game_info.exception.GameNotFoundException;
import com.vou.backend.game.game_info.model.GameCampaign;
import com.vou.backend.game.game_info.model.GameInfo;
import com.vou.backend.game.game_info.model.UserCampaignGame;
import com.vou.backend.game.game_info.repository.GameCampaignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameCampaignService {
    private final GameCampaignRepository gameCampaignRepository;
    private final GameService gameService;

    public List<GameCampaign> findAll() {
        return gameCampaignRepository.findAll();
    }

    public GameCampaign findById(Long id) throws GameCampaignNotFoundException {
        return gameCampaignRepository.findById(id)
                .orElseThrow(() -> new GameCampaignNotFoundException("GameCampaign not found with id: " + id));
    }

    public List<GameCampaign> findByCampaignId(Long campaignId) {
        return gameCampaignRepository.findByCampaignId(campaignId);
    }

    public GameCampaign createGameCampaign(GameCampaign gameCampaign) throws GameNotFoundException {
        GameInfo gameInfo = gameService.findById(gameCampaign.getGameInfo().getId());
        gameCampaign.setGameInfo(gameInfo);
        return gameCampaignRepository.save(gameCampaign);
    }

    public GameCampaign updateGameCampaign(Long id, GameCampaign gameCampaign) throws GameCampaignNotFoundException, GameNotFoundException {
        GameCampaign existingGameCampaign = findById(id);
        GameInfo gameInfo = gameService.findById(gameCampaign.getGameInfo().getId());
        existingGameCampaign.setGameInfo(gameInfo);
        existingGameCampaign.copy(gameCampaign);
        return gameCampaignRepository.save(existingGameCampaign);
    }

    public void updateGameCampaign(GameCampaign gameCampaign) {
        gameCampaignRepository.save(gameCampaign);
    }

    public void delete(Long id) throws GameCampaignNotFoundException {
        if (!gameCampaignRepository.existsById(id)) {
            throw new GameCampaignNotFoundException("GameCampaign not found with id: " + id);
        }
        gameCampaignRepository.deleteById(id);
    }

    public List<UserCampaignGame> getUserCampaignGames(Long campaignId) throws GameCampaignNotFoundException {
        return gameCampaignRepository.findById(campaignId).orElseThrow(() -> new GameCampaignNotFoundException("GameCampaign not found with id: " + campaignId)).getUserCampaignGames();
    }
}
