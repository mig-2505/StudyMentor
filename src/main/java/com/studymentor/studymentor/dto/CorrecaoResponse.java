package com.studymentor.studymentor.dto;

import java.util.List;
import java.util.UUID;

public record CorrecaoResponse(

        UUID quizId,

        Integer totalQuestoes,

        Integer acertos,

        Integer erros,

        Double percentualAcertos,

        List<ResultadoQuestaoDTO> resultados

) {

    public record ResultadoQuestaoDTO(

            Integer questaoId,

            String respostaAluno,

            String respostaCorreta,

            Boolean acertou,

            String explicacao

    ) {}
}
