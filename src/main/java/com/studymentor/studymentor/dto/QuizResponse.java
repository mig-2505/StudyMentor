package com.studymentor.studymentor.dto;

import java.util.List;
import java.util.UUID;

public record QuizResponse(

        UUID id,

        String assunto,

        String dificuldade,

        List<QuestaoDTO> questoes

) {}
