package com.vou.backend.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GiftLinkRequest {
    @NotNull(message = "senderId is required")
    private Long senderId;
    @Pattern(regexp = "^(points|item)$", message = "Gender must be 'points' or 'item'")
    @NotNull(message = "type is required")
    private String type;
    @NotNull(message = "content is required")
    private int content;
}

