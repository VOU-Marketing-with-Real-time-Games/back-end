package com.vou.backend.game.game_info.service;


import com.vou.backend.game.game_info.exception.GameNotFoundException;
import com.vou.backend.game.game_info.model.GameInfo;
import com.vou.backend.game.game_info.repository.GameInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameInfoRepository gameInfoRepository;

    public List<GameInfo> findAll() {
        return gameInfoRepository.findAll();
    }

    public GameInfo findById(Long id) throws GameNotFoundException {
        validateId(id);
        return gameInfoRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game not found with id: " + id));
    }

    // Only update game info
    public GameInfo createGame(GameInfo gameInfo) {
        return gameInfoRepository.save(gameInfo);
    }

    public GameInfo updateGame(Long id, GameInfo gameInfo) throws GameNotFoundException {
        validateId(id);
        GameInfo existingGameInfo = findById(id);
        existingGameInfo.copy(gameInfo);
        return gameInfoRepository.save(existingGameInfo);
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
    }
}
