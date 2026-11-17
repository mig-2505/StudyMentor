package com.studymentor.studymentor.controller;

import com.studymentor.studymentor.dto.GerarQuizRequest;
import com.studymentor.studymentor.dto.QuizResponse;
import com.studymentor.studymentor.service.QuizService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/gerar")
    public ResponseEntity<QuizResponse> gerarQuiz(
            @Valid @RequestBody GerarQuizRequest request
    ) {

        QuizResponse resposta = quizService.gerarQuiz(request);

        return ResponseEntity.ok(resposta);
    }
}
