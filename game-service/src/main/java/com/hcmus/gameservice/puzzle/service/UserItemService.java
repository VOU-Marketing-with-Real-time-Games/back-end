package com.hcmus.gameservice.puzzle.service;

import com.hcmus.gameservice.game_info.model.GameCampaign;
import com.hcmus.gameservice.game_info.repository.GameCampaignRepository;
import com.hcmus.gameservice.puzzle.dto.ItemResponseDto;
import com.hcmus.gameservice.puzzle.dto.UserItemDto;
import com.hcmus.gameservice.puzzle.model.Item;
import com.hcmus.gameservice.puzzle.model.Puzzle;
import com.hcmus.gameservice.puzzle.model.UserItem;
import com.hcmus.gameservice.puzzle.repository.UserItemRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserItemService {
    private final UserItemRepository userItemRepository;
    private final ItemService itemService;
    private final ModelMapper modelMapper;
    private final PuzzleService puzzleService;
    private final GameCampaignRepository gameCampaignRepository;
//    private final VoucherCampaignService voucherCampaignService;
//    private final NotificationService notificationService;
//    private final UserService userService;

//    public ItemResponseDto addRandomItemToUser(Long userId, Long puzzleId) throws Exception {
//        Item item = itemService.getRandomItemByPuzzleId(puzzleId);
//        if (item == null) {
//            return null;
//        }
//
//        itemService.updateRemainingNum(item.getId());
//
//        // Add to user item
//        UserItem userItem = userItemRepository.findByUserIdAndItemId(userId, item.getId());
//        if(userItem == null)
//        {
//            userItem = UserItem.builder().userId(userId).item(item).totalItem(1).build();
//            userItemRepository.save(userItem);
//        }
//        else
//        {
//            userItem.setTotalItem(userItem.getTotalItem() + 1);
//        }
//
//        checkReceiveVoucherAndNotify(userId, puzzleId);
//
//        return modelMapper.map(item, ItemResponseDto.class);
//    }

//    public void checkReceiveVoucherAndNotify(Long userId, Long puzzleId) throws Exception {
//        Puzzle puzzle = puzzleService.getById(puzzleId);
//        List<UserItem> userItems = userItemRepository.findByUserIdAndPuzzleId(userId, puzzleId);
//
//        if(userItems.size() == puzzle.getItemNum())
//        {
//            // Get voucher
//            GameCampaign gameCampaign = gameCampaignRepository.findByGameTypeAndGameId(GameType.SHAKE_GAME, puzzleId);
//            voucherCampaignService.takeVoucherToUser(gameCampaign.getCampaignId(), userId);
//
//            // Notify to user
//            NotificationDto notificationDto = NotificationDto.builder()
//                    .content("You have completed the puzzle " + puzzle.getName() + ". You have received a voucher.")
//                    .userId(userId)
//                    .build();
//            notificationService.addNotification(notificationDto);
//
//            // Decrease total item
//            for(UserItem ui : userItems)
//            {
//                ui.setTotalItem(ui.getTotalItem() - 1);
//                userItemRepository.save(ui);
//            }
//        }
//    }

    public List<UserItemDto> getUserItemsByUserId(Long userId) {
        List<UserItem> userItems = userItemRepository.findByUserId(userId);
        return userItems.stream()
                .map(userItem -> modelMapper.map(userItem, UserItemDto.class))
                .toList();
    }

    public List<UserItemDto> getUserItemsByUserIdAndPuzzleId(Long userId, Long puzzleId) {
        List<UserItem> userItems = userItemRepository.findByUserIdAndPuzzleId(userId, puzzleId);
        return userItems.stream()
                .map(userItem -> modelMapper.map(userItem, UserItemDto.class))
                .toList();
    }
}
