package com.studymentor.studymentor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class StatusController {

    @GetMapping("/status")
    public Map<String, String> verificarStatus() {

        return Map.of(
                "aplicacao", "StudyMentor",
                "status", "ONLINE",
                "mensagem", "API funcionando corretamente!"
        );
    }
}