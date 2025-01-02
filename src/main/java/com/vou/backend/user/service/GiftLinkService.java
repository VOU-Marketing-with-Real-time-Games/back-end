package com.vou.backend.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vou.backend.game.puzzle.model.UserItem;
import com.vou.backend.game.puzzle.repository.UserItemRepository;
import com.vou.backend.user.dto.GiftLinkRequest;
import com.vou.backend.user.dto.RedeemGiftRequest;
import com.vou.backend.user.dto.RedeemGiftRespondDto;
import com.vou.backend.user.exception.InvalidRedeemException;
import com.vou.backend.user.exception.UserNotFoundException;
import com.vou.backend.user.model.Gift;
import com.vou.backend.user.model.User;
import com.vou.backend.user.repository.GiftLinkRepository;
import com.vou.backend.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class GiftLinkService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GiftLinkRepository giftLinkRepository;

    @Autowired
    private UserItemRepository userItemRepository;

    public String createGiftToken(GiftLinkRequest giftLinkRequestDto)
            throws UserNotFoundException, InvalidRedeemException {
        User sender = userRepository.findById(giftLinkRequestDto.getSenderId())
                .orElseThrow(() -> new UserNotFoundException("Sender not found"));

        if (sender.getTurnNum() < giftLinkRequestDto.getContent()) {
            throw new InvalidRedeemException("Insufficient points");
        }

        sender.setTurnNum(sender.getTurnNum() - giftLinkRequestDto.getContent());
        userRepository.save(sender);

        String token = UUID.randomUUID().toString();
        Gift giftLink = new Gift();
        giftLink.setSenderId(giftLinkRequestDto.getSenderId());
        giftLink.setType("points");
        giftLink.setContent(giftLinkRequestDto.getContent());
        giftLink.setToken(token);
        giftLink.setCreatedAt(LocalDateTime.now());
        giftLinkRepository.save(giftLink);

        return token;
    }

    public RedeemGiftRespondDto redeemGiftLink(RedeemGiftRequest redeemGiftRequestDto)
            throws InvalidRedeemException, UserNotFoundException {
        Gift giftLink = giftLinkRepository.findByToken(redeemGiftRequestDto.getToken())
                .orElseThrow(() -> new InvalidRedeemException("Invalid or expired gift link"));

        if (giftLink.getRedeemedAt() != null) {
            throw new InvalidRedeemException("Gift link already redeemed");
        }

        User sender = userRepository.findById(giftLink.getSenderId())
                .orElseThrow(() -> new UserNotFoundException("Sender not found"));
        if (sender == null) {
            throw new InvalidRedeemException("Invalid trade item");
        }

        if (giftLink.getType().equals("points")) {
            if (sender.getTurnNum() < 1) {
                throw new InvalidRedeemException("Invalid trade item");
            }
            sender.setTurnNum(sender.getTurnNum() - 1);
            User receiver = userRepository.findById(redeemGiftRequestDto.getReceiverId())
                    .orElseThrow(() -> new InvalidRedeemException("Receiver not found"));
            receiver.setTurnNum(receiver.getTurnNum() + (int) giftLink.getContent());
            userRepository.save(receiver);
            userRepository.save(sender);
        }
        if (giftLink.getType().equals("item")) {
            UserItem userItem = userItemRepository.findByUserIdAndItemId(giftLink.getSenderId(),
                    giftLink.getContent());

            if (userItem == null || userItem.getTotalItem() < 1) {
                throw new InvalidRedeemException("Invalid trade item");
            }
            userItem.setTotalItem(userItem.getTotalItem() - 1);

            UserItem userItemReceiver = userItemRepository.findByUserIdAndItemId(redeemGiftRequestDto.getReceiverId(),
                    giftLink.getContent());
            if (userItemReceiver == null) {
                userItemReceiver = UserItem.builder().userId(sender.getId()).item(userItem.getItem())
                        .totalItem(1).build();
                userItemRepository.save(userItemReceiver);
                // checkReceiveVoucherAndNotify(user.getId(),
                // userItem.getItem().getPuzzle().getId());
            } else {
                userItemReceiver.setTotalItem(userItemReceiver.getTotalItem() + 1);
            }
        }

        giftLink.setReceiverId(redeemGiftRequestDto.getReceiverId());
        giftLink.setRedeemedAt(LocalDateTime.now());
        giftLinkRepository.save(giftLink);

        return new RedeemGiftRespondDto(giftLink.getType(), giftLink.getContent());

    }

    public boolean isGiftExist(String token) {
        return giftLinkRepository.existsByToken(token);
    }
}
