package com.studymentor.studymentor.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CorrecaoRequest(

        @NotNull(message = "O ID do quiz e obrigatorio")
        UUID quizId,

        @NotEmpty(message = "Informe as respostas do estudante")
        @Size(
                max = 10,
                message = "O quiz permite no maximo 10 respostas"
        )
        List<@NotNull(message = "A resposta nao pode ser nula")
        @Valid RespostaAlunoDTO> respostas

) {}

