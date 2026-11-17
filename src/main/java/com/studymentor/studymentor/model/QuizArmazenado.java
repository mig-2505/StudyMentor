package com.studymentor.studymentor.model;

import java.util.List;
import java.util.UUID;

public record QuizArmazenado(

        UUID id,

        String assunto,

        String dificuldade,

        List<QuestaoGeradaIA> questoes

) {

    public QuizArmazenado {
        questoes = List.copyOf(questoes);
    }
}
