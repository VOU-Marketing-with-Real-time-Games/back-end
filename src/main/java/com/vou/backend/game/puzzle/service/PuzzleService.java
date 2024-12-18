package com.vou.backend.game.puzzle.service;

import com.vou.backend.game.game_info.exception.GameCampaignNotFoundException;
import com.vou.backend.game.game_info.exception.GameNotFoundException;
import com.vou.backend.game.game_info.exception.Game_CampaignGameConflict;
import com.vou.backend.game.game_info.exception.PuzzleNotFoundException;
import com.vou.backend.game.game_info.model.GameCampaign;
import com.vou.backend.game.game_info.model.GameType;
import com.vou.backend.game.game_info.service.GameCampaignService;
import com.vou.backend.game.puzzle.dto.PuzzleRequestDto;
import com.vou.backend.game.puzzle.dto.PuzzleResponseDto;
import com.vou.backend.game.puzzle.model.Item;
import com.vou.backend.game.puzzle.model.Puzzle;
import com.vou.backend.game.puzzle.repository.PuzzleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PuzzleService {

    private final PuzzleRepository puzzleRepository;
    private final GameCampaignService gameCampaignService;
    private final ItemService itemService;
    private final ModelMapper modelMapper;

    public PuzzleResponseDto createPuzzle(PuzzleRequestDto puzzleRequestDto) throws GameCampaignNotFoundException, Game_CampaignGameConflict {
        Puzzle puzzle = modelMapper.map(puzzleRequestDto, Puzzle.class);

        GameCampaign gameCampaign = gameCampaignService.getById(puzzle.getCampaignGameId());
        if(gameCampaign.getGameInfo().getType() != GameType.SHAKE_GAME)
        {
            throw new Game_CampaignGameConflict("Game Campaign does not match with the game type");
        }
        for(Item item : puzzle.getItems()) {
            item.setRemainingNum(item.getTotal());
            item.setPuzzle(puzzle);
        }

        Puzzle savedPuzzle = puzzleRepository.save(puzzle);
        gameCampaign.setGameId(savedPuzzle.getId());
        gameCampaignService.updateGameCampaign(gameCampaign);

        return modelMapper.map(savedPuzzle, PuzzleResponseDto.class);
    }

    public PuzzleResponseDto updatePuzzle(Long id, PuzzleRequestDto puzzleRequestDto) throws PuzzleNotFoundException, GameCampaignNotFoundException, Game_CampaignGameConflict {
        Puzzle puzzle = modelMapper.map(puzzleRequestDto, Puzzle.class);

        GameCampaign gameCampaign = gameCampaignService.getById(puzzle.getCampaignGameId());
        if(gameCampaign.getGameInfo().getType() != GameType.SHAKE_GAME)
        {
            throw new Game_CampaignGameConflict("Game Campaign does not match with the game type");
        }
        Puzzle existingPuzzle = puzzleRepository.findById(id)
                .orElseThrow(() -> new PuzzleNotFoundException("Puzzle not found with id: " + id));

        itemService.deleteItemByPuzzleId(id);

        for(Item item : puzzle.getItems()) {
            item.setRemainingNum(item.getTotal());
            item.setPuzzle(existingPuzzle);
        }
        existingPuzzle.copy(puzzle);
        Puzzle updatedPuzzle = puzzleRepository.save(existingPuzzle);
        return modelMapper.map(updatedPuzzle, PuzzleResponseDto.class);
    }

    public PuzzleResponseDto getPuzzleById(Long id) throws PuzzleNotFoundException {
        Puzzle puzzle = puzzleRepository.findById(id)
                .orElseThrow(() -> new PuzzleNotFoundException("Puzzle not found"));
        return modelMapper.map(puzzle, PuzzleResponseDto.class);
    }

    public List<PuzzleResponseDto> getAllPuzzles() {
        var puzzles = puzzleRepository.findAll();
        return puzzles.stream()
                .map(puzzle -> modelMapper.map(puzzle, PuzzleResponseDto.class))
                .collect(Collectors.toList());
    }

    public void deletePuzzle(Long id) throws PuzzleNotFoundException, GameCampaignNotFoundException, GameNotFoundException {
        Puzzle puzzle = puzzleRepository.findById(id)
                .orElseThrow(() -> new PuzzleNotFoundException("Puzzle not found"));
        GameCampaign gameCampaign = gameCampaignService.getById(puzzle.getCampaignGameId());
        gameCampaign.setGameId(null);
        puzzleRepository.deleteById(id);
    }
}
