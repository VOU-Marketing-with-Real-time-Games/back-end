package com.vou.backend.game.puzzle.service;

import com.vou.backend.game.puzzle.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
