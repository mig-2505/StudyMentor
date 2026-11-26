package com.studymentor.studymentor.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RespostaAlunoDTO(

        @NotNull(message = "O ID da questao e obrigatorio")
        @Min(
                value = 1,
                message = "O ID da questao deve ser maior que zero"
        )
        Integer questaoId,

        @NotBlank(message = "A alternativa e obrigatoria")
        @Pattern(
                regexp = "A|B|C|D",
                message = "A alternativa deve ser A, B, C ou D"
        )
        String alternativa

) {}

