package com.studymentor.studymentor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(
        name = "Status",
        description = "Operacoes para verificar o funcionamento da API"
)
public class StatusController {

    @Operation(
            summary = "Verificar status",
            description = "Retorna informacoes basicas sobre o funcionamento do StudyMentor."
    )
    @ApiResponse(
            responseCode = "200",
            description = "API funcionando corretamente"
    )
    @GetMapping("/status")
    public Map<String, String> verificarStatus() {

        return Map.of(
                "aplicacao", "StudyMentor",
                "status", "ONLINE",
                "mensagem", "API funcionando corretamente!"
        );
    }
}
