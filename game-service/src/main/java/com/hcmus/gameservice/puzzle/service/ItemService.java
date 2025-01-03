package com.hcmus.gameservice.puzzle.service;

import com.hcmus.gameservice.puzzle.model.Item;
import com.hcmus.gameservice.puzzle.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void deleteItemByPuzzleId(Long puzzleId) {
        itemRepository.deleteByPuzzleId(puzzleId);
    }


    @Transactional(readOnly = true)
    public Item getRandomItemByPuzzleId(Long puzzleId) {
        List<Item> items = itemRepository.findByPuzzleId(puzzleId);

        if (items.isEmpty()) {
            return null;
        }

        int totalRemaining = items.stream().mapToInt(Item::getRemainingNum).sum();
        int randomIndex = new Random().nextInt(totalRemaining);

        int currentSum = 0;
        for (Item item : items) {
            currentSum += item.getRemainingNum();
            if (randomIndex < currentSum) {
                return item;
            }
        }

        return null;
    }

    public void updateRemainingNum(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        item.setRemainingNum(item.getRemainingNum() - 1);
    }

}
