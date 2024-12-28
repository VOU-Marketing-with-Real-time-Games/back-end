package com.vou.backend.filter.game.puzzle.service;

import com.vou.backend.filter.game.puzzle.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void deleteItemByPuzzleId(Long puzzleId) {
        itemRepository.deleteByPuzzleId(puzzleId);
    }
}
