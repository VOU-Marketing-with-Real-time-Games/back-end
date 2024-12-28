package com.vou.backend.game.quizz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionRequestDto {
    @NotNull(message = "Question name cannot be null")
    @NotBlank(message = "Question name cannot be blank")
    private String questionName;

    @NotNull(message = "Option 1 cannot be null")
    @NotBlank(message = "Option 1 cannot be blank")
    private String option1;

    @NotNull(message = "Option 2 cannot be null")
    @NotBlank(message = "Option 2 cannot be blank")
    private String option2;

    @NotNull(message = "Option 3 cannot be null")
    @NotBlank(message = "Option 3 cannot be blank")
    private String option3;

    @NotNull(message = "Option 4 cannot be null")
    @NotBlank(message = "Option 4 cannot be blank")
    private String option4;

    @NotNull(message = "Answer cannot be null")
    @NotBlank(message = "Answer cannot be blank")
    private String answer;

    private String explaination;

    @NotNull(message = "Quizz id cannot be null")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Long quizzId;
}
