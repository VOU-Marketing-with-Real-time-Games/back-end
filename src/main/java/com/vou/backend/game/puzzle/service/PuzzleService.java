package com.vou.backend.game.puzzle.service;

import com.vou.backend.game.game_info.exception.GameCampaignNotFoundException;
import com.vou.backend.game.game_info.exception.GameNotFoundException;
import com.vou.backend.game.game_info.exception.Game_CampaignGameConflict;
import com.vou.backend.game.game_info.exception.PuzzleNotFoundException;
import com.vou.backend.game.game_info.model.GameCampaign;
import com.vou.backend.game.game_info.model.GameType;
import com.vou.backend.game.game_info.service.GameCampaignService;
import com.vou.backend.game.puzzle.model.Item;
import com.vou.backend.game.puzzle.model.Puzzle;
import com.vou.backend.game.puzzle.repository.PuzzleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PuzzleService {

    private final PuzzleRepository puzzleRepository;
    private final GameCampaignService gameCampaignService;
    private final ItemService itemService;

    public Puzzle createPuzzle(Puzzle puzzle) throws GameCampaignNotFoundException, Game_CampaignGameConflict {
        GameCampaign gameCampaign = gameCampaignService.findById(puzzle.getCampaignGameId());
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

        return savedPuzzle;
    }

    public Puzzle updatePuzzle(Long id, Puzzle puzzle) throws PuzzleNotFoundException, GameCampaignNotFoundException, Game_CampaignGameConflict {
        GameCampaign gameCampaign = gameCampaignService.findById(puzzle.getCampaignGameId());
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
        return puzzleRepository.save(existingPuzzle);
    }

    public Puzzle getPuzzleById(Long id) throws PuzzleNotFoundException {
        return puzzleRepository.findById(id)
                .orElseThrow(() -> new PuzzleNotFoundException("Puzzle not found"));
    }

    public List<Puzzle> getAllPuzzles() {
        return puzzleRepository.findAll();
    }

    public void deletePuzzle(Long id) throws PuzzleNotFoundException, GameCampaignNotFoundException, GameNotFoundException {
        Puzzle puzzle = getPuzzleById(id);
        GameCampaign gameCampaign = gameCampaignService.findById(puzzle.getCampaignGameId());
        gameCampaign.setGameId(null);
        puzzleRepository.deleteById(id);
    }
}
