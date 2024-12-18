package com.vou.backend.game.game_info.service;

import com.vou.backend.game.game_info.dto.GameCampaignRequestDto;
import com.vou.backend.game.game_info.dto.GameCampaignResponseDto;
import com.vou.backend.game.game_info.dto.UserCampaignGameResponseDto;
import com.vou.backend.game.game_info.exception.GameCampaignNotFoundException;
import com.vou.backend.game.game_info.exception.GameNotFoundException;
import com.vou.backend.game.game_info.model.GameCampaign;
import com.vou.backend.game.game_info.model.GameInfo;
import com.vou.backend.game.game_info.model.UserCampaignGame;
import com.vou.backend.game.game_info.repository.GameCampaignRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameCampaignService {
    private final GameCampaignRepository gameCampaignRepository;
    private final GameService gameService;
    private final ModelMapper modelMapper;

    public List<GameCampaignResponseDto> findAll() {
        List<GameCampaign> gameCampaigns = gameCampaignRepository.findAll();
        return gameCampaigns.stream()
                .map(gameCampaign -> modelMapper.map(gameCampaign, GameCampaignResponseDto.class))
                .collect(Collectors.toList());
    }

    public GameCampaignResponseDto findById(Long id) throws GameCampaignNotFoundException {
        GameCampaign gameCampaign = gameCampaignRepository.findById(id)
                .orElseThrow(() -> new GameCampaignNotFoundException("GameCampaign not found with id: " + id));
        return modelMapper.map(gameCampaign, GameCampaignResponseDto.class);
    }

    public GameCampaign getById(Long id) throws GameCampaignNotFoundException {
        return gameCampaignRepository.findById(id)
                .orElseThrow(() -> new GameCampaignNotFoundException("GameCampaign not found with id: " + id));
    }

    public List<GameCampaignResponseDto> findByCampaignId(Long campaignId) {
        List<GameCampaign> gameCampaigns =  gameCampaignRepository.findByCampaignId(campaignId);
        return gameCampaigns.stream()
                .map(gameCampaign -> modelMapper.map(gameCampaign, GameCampaignResponseDto.class))
                .collect(Collectors.toList());
    }

    public GameCampaignResponseDto createGameCampaign(GameCampaignRequestDto gameCampaignDto) throws GameNotFoundException {
        GameCampaign gameCampaign = modelMapper.map(gameCampaignDto, GameCampaign.class);
        GameInfo gameInfo = gameService.getById(gameCampaign.getGameInfo().getId());
        gameCampaign.setGameInfo(gameInfo);
        GameCampaign savedGameCampaign = gameCampaignRepository.save(gameCampaign);
        return modelMapper.map(savedGameCampaign, GameCampaignResponseDto.class);
    }

    public GameCampaignResponseDto updateGameCampaign(Long id, GameCampaignRequestDto gameCampaignDto) throws GameCampaignNotFoundException, GameNotFoundException {
        GameCampaign gameCampaign = modelMapper.map(gameCampaignDto, GameCampaign.class);
        GameCampaign existingGameCampaign = gameCampaignRepository.findById(id)
                .orElseThrow(() -> new GameCampaignNotFoundException("GameCampaign not found with id: " + id));
        GameInfo gameInfo = gameService.getById(gameCampaign.getGameInfo().getId());
        existingGameCampaign.setGameInfo(gameInfo);
        existingGameCampaign.copy(gameCampaign);
        GameCampaign updatedGameCampaign =  gameCampaignRepository.save(existingGameCampaign);
        return modelMapper.map(updatedGameCampaign, GameCampaignResponseDto.class);
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

    public List<UserCampaignGameResponseDto> getUserCampaignGames(Long campaignId) throws GameCampaignNotFoundException {
        List<UserCampaignGame> userCampaignGames = gameCampaignRepository.findById(campaignId).orElseThrow(() -> new GameCampaignNotFoundException("GameCampaign not found with id: " + campaignId)).getUserCampaignGames();
        return userCampaignGames.stream()
                .map(userCampaignGame -> modelMapper.map(userCampaignGame, UserCampaignGameResponseDto.class))
                .collect(Collectors.toList());
    }
}
