package com.studymentor.studymentor.controller;

import com.studymentor.studymentor.dto.CorrecaoRequest;
import com.studymentor.studymentor.dto.CorrecaoResponse;
import com.studymentor.studymentor.service.CorrecaoService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quizzes")
public class CorrecaoController {

    private final CorrecaoService correcaoService;

    public CorrecaoController(
            CorrecaoService correcaoService
    ) {
        this.correcaoService = correcaoService;
    }

    @PostMapping("/corrigir")
    public ResponseEntity<CorrecaoResponse> corrigir(
            @Valid @RequestBody CorrecaoRequest request
    ) {

        CorrecaoResponse resposta =
                correcaoService.corrigir(request);

        return ResponseEntity.ok(resposta);
    }
}

