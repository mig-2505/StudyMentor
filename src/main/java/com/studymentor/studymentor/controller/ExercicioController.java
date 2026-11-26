package com.studymentor.studymentor.controller;

import com.studymentor.studymentor.dto.ExercicioRequest;
import com.studymentor.studymentor.dto.ExercicioResponse;
import com.studymentor.studymentor.service.ExercicioService;

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
@RequestMapping("/api/exercicios")
@Tag(
        name = "Exercicios",
        description = "Geracao de exercicios educativos simples com IA"
)
public class ExercicioController {

    private final ExercicioService exercicioService;

    public ExercicioController(
            ExercicioService exercicioService
    ) {
        this.exercicioService = exercicioService;
    }

    @Operation(
            summary = "Gerar exercicio com IA",
            description = """
                    Recebe um assunto e gera uma questao
                    educativa de multipla escolha utilizando
                    Spring AI.

                    A resposta e retornada em formato textual.
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Exercicio gerado com sucesso"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Dados da requisicao invalidos"
    )
    @PostMapping("/gerar")
    public ResponseEntity<ExercicioResponse> gerar(
            @Valid @RequestBody ExercicioRequest request
    ) {

        ExercicioResponse resposta =
                exercicioService.gerarExercicio(
                        request.assunto()
                );

        return ResponseEntity.ok(resposta);
    }
}

