package com.vou.backend.game.puzzle.service;

import com.vou.backend.game.puzzle.model.Item;
import com.vou.backend.game.puzzle.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
