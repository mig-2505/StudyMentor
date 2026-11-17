package com.studymentor.studymentor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExercicioRequest(

        @NotBlank(message = "O assunto e obrigatorio")
        @Size(max = 100, message = "O assunto deve ter no maximo 100 caracteres")
        String assunto

) {}
