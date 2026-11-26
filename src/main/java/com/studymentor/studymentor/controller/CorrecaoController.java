package com.studymentor.studymentor.controller;

import com.studymentor.studymentor.dto.CorrecaoRequest;
import com.studymentor.studymentor.dto.CorrecaoResponse;
import com.studymentor.studymentor.service.CorrecaoService;

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
        description = "Geracao e correcao de questionarios"
)
public class CorrecaoController {

    private final CorrecaoService correcaoService;

    public CorrecaoController(
            CorrecaoService correcaoService
    ) {
        this.correcaoService = correcaoService;
    }

    @Operation(
            summary = "Corrigir questionario",
            description = """
                    Recebe o UUID de um questionario e
                    as alternativas escolhidas pelo estudante.

                    A aplicacao recupera o gabarito armazenado,
                    compara as respostas e calcula o resultado.

                    A resposta inclui:
                    - Quantidade de acertos
                    - Quantidade de erros
                    - Percentual de acertos
                    - Correcao detalhada de cada questao

                    Nao realiza uma nova chamada a IA.
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Questionario corrigido com sucesso"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Respostas invalidas ou incompletas"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Questionario nao encontrado"
    )
    @PostMapping("/corrigir")
    public ResponseEntity<CorrecaoResponse> corrigir(
            @Valid @RequestBody CorrecaoRequest request
    ) {

        CorrecaoResponse resposta =
                correcaoService.corrigir(request);

        return ResponseEntity.ok(resposta);
    }
}


