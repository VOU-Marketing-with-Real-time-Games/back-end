package com.vou.backend.game.game_info.service;

import com.vou.backend.game.game_info.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
    private GameRepository gameRepository;

}
