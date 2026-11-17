package com.studymentor.studymentor.dto;

import java.util.Map;

public record QuestaoDTO(

        Integer id,

        String enunciado,

        Map<String, String> alternativas

) {}
