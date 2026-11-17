package com.studymentor.studymentor.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record GerarQuizRequest(

        @NotBlank(message = "O assunto e obrigatorio")
        @Size(
                max = 100,
                message = "O assunto deve ter no maximo 100 caracteres"
        )
        String assunto,

        @NotBlank(message = "A dificuldade e obrigatoria")
        @Pattern(
                regexp = "FACIL|MEDIO|DIFICIL",
                message = "A dificuldade deve ser FACIL, MEDIO ou DIFICIL"
        )
        String dificuldade,

        @NotNull(message = "A quantidade e obrigatoria")
        @Min(
                value = 1,
                message = "A quantidade minima e 1"
        )
        @Max(
                value = 10,
                message = "A quantidade maxima e 10"
        )
        Integer quantidade

) {}
