package com.vou.backend.game.puzzle.controller;

import com.vou.backend.game.game_info.exception.GameCampaignNotFoundException;
import com.vou.backend.game.game_info.exception.GameNotFoundException;
import com.vou.backend.game.game_info.exception.Game_CampaignGameConflict;
import com.vou.backend.game.game_info.exception.PuzzleNotFoundException;
import com.vou.backend.game.puzzle.dto.PuzzleRequestDto;
import com.vou.backend.game.puzzle.dto.PuzzleResponseDto;
import com.vou.backend.game.puzzle.model.Puzzle;
import com.vou.backend.game.puzzle.service.PuzzleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/puzzles")
@RequiredArgsConstructor
@Validated
public class PuzzleApiController {

    private final PuzzleService puzzleService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<PuzzleResponseDto> createPuzzle(@Valid @RequestBody PuzzleRequestDto puzzleRequestDto) throws Game_CampaignGameConflict, GameCampaignNotFoundException {
        PuzzleResponseDto responseDto = puzzleService.createPuzzle(puzzleRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PuzzleResponseDto> updatePuzzle(@PathVariable Long id, @Valid @RequestBody PuzzleRequestDto puzzleRequestDto) throws GameCampaignNotFoundException, PuzzleNotFoundException, Game_CampaignGameConflict {
        PuzzleResponseDto responseDto = puzzleService.updatePuzzle(id, puzzleRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PuzzleResponseDto> getPuzzleById(@PathVariable Long id) throws PuzzleNotFoundException {
        PuzzleResponseDto responseDto = puzzleService.getPuzzleById(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PuzzleResponseDto>> getAllPuzzles() {
        var puzzles = puzzleService.getAllPuzzles();
        var responseDtos = puzzles.stream()
                .map(puzzle -> modelMapper.map(puzzle, PuzzleResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePuzzle(@PathVariable Long id) throws GameCampaignNotFoundException, PuzzleNotFoundException, GameNotFoundException {
        puzzleService.deletePuzzle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
