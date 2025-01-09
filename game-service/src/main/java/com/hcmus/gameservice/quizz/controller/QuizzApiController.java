package com.hcmus.gameservice.quizz.controller;

import com.hcmus.gameservice.game_info.exception.GameCampaignNotFoundException;
import com.hcmus.gameservice.game_info.exception.Game_CampaignGameConflict;
import com.hcmus.gameservice.game_info.exception.QuizzNotFoundException;
import com.hcmus.gameservice.game_info.exception.SchedulingErrorException;
import com.hcmus.gameservice.quizz.dto.QuizzRequestDto;
import com.hcmus.gameservice.quizz.dto.QuizzResponseDto;
import com.hcmus.gameservice.quizz.service.QuizzService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/v3/quizzes")
@RequiredArgsConstructor
@Validated
public class QuizzApiController {

    private final QuizzService quizzService;

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
    public ResponseEntity<QuizzResponseDto> createQuizz(@Valid @RequestBody QuizzRequestDto quizzRequestDto) throws GameCampaignNotFoundException, Game_CampaignGameConflict, SchedulingErrorException {
        QuizzResponseDto quizzResponseDto = quizzService.createQuizz(quizzRequestDto);
        return ResponseEntity.ok(quizzResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizzResponseDto> updateQuizz(@PathVariable Long id,@Valid @RequestBody QuizzRequestDto quizzRequestDto) throws QuizzNotFoundException, ParseException, SchedulingErrorException {
        QuizzResponseDto quizzResponseDto = quizzService.updateQuizz(id, quizzRequestDto);
        return ResponseEntity.ok(quizzResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuizz(@PathVariable Long id) throws QuizzNotFoundException, GameCampaignNotFoundException {
        quizzService.deleteQuizz(id);
        return ResponseEntity.noContent().build();
    }
}
