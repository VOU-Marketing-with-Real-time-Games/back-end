package com.vou.backend.filter.game.quizz.controller;

import com.vou.backend.filter.game.game_info.exception.GameCampaignNotFoundException;
import com.vou.backend.filter.game.game_info.exception.Game_CampaignGameConflict;
import com.vou.backend.filter.game.game_info.exception.QuizzNotFoundException;
import com.vou.backend.filter.game.quizz.service.QuizzService;
import com.vou.backend.filter.game.quizz.dto.QuizzRequestDto;
import com.vou.backend.filter.game.quizz.dto.QuizzResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
@RequiredArgsConstructor
@Validated
public class QuizzApiController {

    private final QuizzService quizzService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<QuizzResponseDto>> getAllQuizzes() {
        return ResponseEntity.ok(quizzService.getAllQuizzes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizzResponseDto> getQuizzById(@PathVariable Long id) throws QuizzNotFoundException {
        QuizzResponseDto quizzResponseDto = quizzService.getQuizzById(id);
        return ResponseEntity.ok(quizzResponseDto);
    }

    @PostMapping
    public ResponseEntity<QuizzResponseDto> createQuizz(@Valid @RequestBody QuizzRequestDto quizzRequestDto) throws GameCampaignNotFoundException, Game_CampaignGameConflict {
        QuizzResponseDto quizzResponseDto = quizzService.createQuizz(quizzRequestDto);
        return ResponseEntity.ok(quizzResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizzResponseDto> updateQuizz(@PathVariable Long id,@Valid @RequestBody QuizzRequestDto quizzRequestDto) throws QuizzNotFoundException {
        QuizzResponseDto quizzResponseDto = quizzService.updateQuizz(id, quizzRequestDto);
        return ResponseEntity.ok(quizzResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizz(@PathVariable Long id) throws QuizzNotFoundException, GameCampaignNotFoundException {
        quizzService.deleteQuizz(id);
        return ResponseEntity.noContent().build();
    }
}
