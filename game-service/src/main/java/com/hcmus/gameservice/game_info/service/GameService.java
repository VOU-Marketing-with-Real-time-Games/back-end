package com.hcmus.gameservice.game_info.service;

import com.hcmus.gameservice.game_info.dto.GameInfoDto;
import com.hcmus.gameservice.game_info.exception.GameNotFoundException;
import com.hcmus.gameservice.game_info.model.GameInfo;
import com.hcmus.gameservice.game_info.repository.GameInfoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameInfoRepository gameInfoRepository;
    private final ModelMapper modelMapper;

    public List<GameInfoDto> findAll() {
        return gameInfoRepository.findAll().stream()
                .map(gameInfo -> modelMapper.map(gameInfo, GameInfoDto.class))
                .collect(Collectors.toList());
    }

    public GameInfoDto findById(Long id) throws GameNotFoundException {
        validateId(id);
        GameInfo gameInfo =  gameInfoRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game not found with id: " + id));
        return modelMapper.map(gameInfo, GameInfoDto.class);
    }

    // Return game info for the given id (for communication between services)
    public GameInfo getById(Long id) throws GameNotFoundException {
        validateId(id);
        return gameInfoRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game not found with id: " + id));
    }

    public GameInfoDto updateGame(Long id, GameInfoDto gameInfoDto) throws GameNotFoundException {
        validateId(id);
        GameInfo gameInfo = modelMapper.map(gameInfoDto, GameInfo.class);
        GameInfo existingGameInfo = gameInfoRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game not found with id: " + id));
        existingGameInfo.copy(gameInfo);
        GameInfo updatedGameInfo = gameInfoRepository.save(existingGameInfo);
        return modelMapper.map(updatedGameInfo, GameInfoDto.class);
    }

    private void validateId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
    }

    public GameInfoDto addGame(GameInfoDto gameInfoDto) {
        GameInfo gameInfo = modelMapper.map(gameInfoDto, GameInfo.class);
        GameInfo savedGameInfo = gameInfoRepository.save(gameInfo);
        return modelMapper.map(savedGameInfo, GameInfoDto.class);
    }
}
