package com.studymentor.studymentor.controller;

import com.studymentor.studymentor.dto.ExercicioRequest;
import com.studymentor.studymentor.dto.ExercicioResponse;
import com.studymentor.studymentor.service.ExercicioService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exercicios")
public class ExercicioController {

    private final ExercicioService exercicioService;

    public ExercicioController(
            ExercicioService exercicioService) {

        this.exercicioService = exercicioService;
    }

    @PostMapping("/gerar")
    public ResponseEntity<ExercicioResponse> gerar(@Valid @RequestBody ExercicioRequest request) {

        ExercicioResponse resposta =
                exercicioService.gerarExercicio(
                        request.assunto()
                );

        return ResponseEntity.ok(resposta);
    }
}
