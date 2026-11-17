package com.studymentor.studymentor.model;

import java.util.Map;

public record QuestaoGeradaIA(

        String enunciado,

        Map<String, String> alternativas,

        String respostaCorreta,

        String explicacao

) {}
