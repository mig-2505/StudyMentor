package com.studymentor.studymentor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarValidacao(
            MethodArgumentNotValidException exception
    ) {

        Map<String, String> erros = new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(erro ->
                        erros.put(
                                erro.getField(),
                                erro.getDefaultMessage()
                        )
                );

        Map<String, Object> resposta = new LinkedHashMap<>();

        resposta.put("status", 400);
        resposta.put("erro", "Dados invalidos");
        resposta.put("detalhes", erros);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resposta);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> tratarArgumento(
            IllegalArgumentException exception
    ) {

        Map<String, Object> resposta = new LinkedHashMap<>();

        resposta.put("status", 400);
        resposta.put("erro", "Requisicao invalida");
        resposta.put("mensagem", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resposta);
    }

    @ExceptionHandler(QuizNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> tratarQuizNaoEncontrado(
            QuizNaoEncontradoException exception
    ) {

        Map<String, Object> resposta = new LinkedHashMap<>();

        resposta.put("status", 404);
        resposta.put("erro", "Quiz nao encontrado");
        resposta.put("mensagem", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(resposta);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> tratarJsonInvalido(
            HttpMessageNotReadableException exception
    ) {

        Map<String, Object> resposta = new LinkedHashMap<>();

        resposta.put("status", 400);
        resposta.put("erro", "JSON invalido");
        resposta.put(
                "mensagem",
                "Verifique o formato dos dados enviados."
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resposta);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> tratarGeracao(
            IllegalStateException exception
    ) {

        Map<String, Object> resposta = new LinkedHashMap<>();

        resposta.put("status", 502);
        resposta.put("erro", "Falha na geracao do questionario");
        resposta.put(
                "mensagem",
                "Nao foi possivel gerar um quiz valido."
        );

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(resposta);
    }
}

