package com.studymentor.studymentor.controller;

import com.studymentor.studymentor.dto.GerarQuizRequest;
import com.studymentor.studymentor.dto.QuizResponse;
import com.studymentor.studymentor.service.QuizService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quizzes")
@Tag(
        name = "Questionarios",
        description = "Geracao de questionarios estruturados com inteligencia artificial"
)
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @Operation(
            summary = "Gerar questionario com IA",
            description = """
                    Gera um questionario de multipla escolha
                    utilizando Spring AI.

                    Parametros:
                    - Assunto do questionario
                    - Dificuldade: FACIL, MEDIO ou DIFICIL
                    - Quantidade: entre 1 e 10 questoes

                    Cada questao possui quatro alternativas.

                    O gabarito nao e enviado ao estudante.
                    Ele permanece armazenado temporariamente
                    no backend para posterior correcao.
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Questionario gerado com sucesso"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Parametros invalidos"
    )
    @ApiResponse(
            responseCode = "502",
            description = "Falha na geracao de um questionario valido"
    )
    @PostMapping("/gerar")
    public ResponseEntity<QuizResponse> gerarQuiz(
            @Valid @RequestBody GerarQuizRequest request
    ) {

        QuizResponse resposta =
                quizService.gerarQuiz(request);

        return ResponseEntity.ok(resposta);
    }
}

