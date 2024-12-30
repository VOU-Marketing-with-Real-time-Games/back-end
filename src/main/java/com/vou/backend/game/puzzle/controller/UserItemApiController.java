package com.vou.backend.game.puzzle.controller;

import com.vou.backend.game.puzzle.dto.ItemResponseDto;
import com.vou.backend.game.puzzle.dto.ItemTradeDto;
import com.vou.backend.game.puzzle.dto.UserItemDto;
import com.vou.backend.game.puzzle.service.UserItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-items")
@RequiredArgsConstructor
public class UserItemApiController {
    private final UserItemService userItemService;

    @GetMapping("/add-random-item/user/{userId}/puzzle/{puzzleId}")
    public ResponseEntity<ItemResponseDto> addRandomItemToUser(@PathVariable Long userId, @PathVariable Long puzzleId) throws Exception {
        ItemResponseDto itemResponseDto = userItemService.addRandomItemToUser(userId, puzzleId);
        if (itemResponseDto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(itemResponseDto);
    }

    @GetMapping("/user/{userId}/puzzle/{puzzleId}")
    public ResponseEntity<List<UserItemDto>> getUserItemByUserIdAndPuzzleId(@PathVariable Long userId, @PathVariable Long puzzleId) {
        List<UserItemDto> itemResponseDto = userItemService.getUserItemsByUserIdAndPuzzleId(userId, puzzleId);
        if (itemResponseDto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(itemResponseDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserItemDto>> getUserItemByUserId(@PathVariable Long userId) {
        List<UserItemDto> itemResponseDto = userItemService.getUserItemsByUserId(userId);
        if (itemResponseDto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(itemResponseDto);
    }
}
