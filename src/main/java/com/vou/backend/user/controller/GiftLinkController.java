package com.vou.backend.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.vou.backend.user.dto.GiftLinkRequest;
import com.vou.backend.user.dto.RedeemGiftRequest;
import com.vou.backend.user.dto.RedeemGiftRespondDto;
import com.vou.backend.user.exception.InvalidRedeemException;
import com.vou.backend.user.exception.UserNotFoundException;
import com.vou.backend.user.service.GiftLinkService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/gift")
public class GiftLinkController {
    @Autowired
    private GiftLinkService giftLinkService;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> createGiftToken(@Valid @RequestBody GiftLinkRequest giftLinkRequestDto)
            throws UserNotFoundException, InvalidRedeemException {
        String token = giftLinkService.createGiftToken(giftLinkRequestDto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/redeem")
    @ResponseBody
    public ResponseEntity<?> redeemGift(@Valid @RequestBody RedeemGiftRequest redeemGiftRequestDto) throws InvalidRedeemException, UserNotFoundException {
        RedeemGiftRespondDto redeemGiftRespondDto = giftLinkService.redeemGiftLink(redeemGiftRequestDto);
        return ResponseEntity.ok(redeemGiftRespondDto);
    }

    @GetMapping("/redeem")
    public String redeemPage(@RequestParam(required = false) String token, Model model) {
        if (token == null || token.isEmpty()) {
            model.addAttribute("error", "Token is missing.");
            return "redeem";
        }
        boolean isValidToken = giftLinkService.isGiftExist(token);
        if (!isValidToken) {
            model.addAttribute("error", "Invalid token.");
            return "redeem";
        }
        model.addAttribute("token", token);
        return "redeem";
    }
}
