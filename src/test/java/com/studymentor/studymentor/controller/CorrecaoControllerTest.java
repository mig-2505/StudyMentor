package com.studymentor.studymentor.controller;

import com.studymentor.studymentor.dto.CorrecaoResponse;
import com.studymentor.studymentor.service.CorrecaoService;
import com.studymentor.studymentor.exception.QuizNaoEncontradoException;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CorrecaoController.class)
class CorrecaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CorrecaoService correcaoService;

    @Test
    void deveRetornarCorrecaoComSucesso() throws Exception {

        UUID quizId = UUID.randomUUID();

        CorrecaoResponse resposta = new CorrecaoResponse(
                quizId,
                1,
                1,
                0,
                100.0,
                List.of(
                        new CorrecaoResponse.ResultadoQuestaoDTO(
                                1,
                                "B",
                                "B",
                                true,
                                "A alternativa B esta correta."
                        )
                )
        );

        when(correcaoService.corrigir(any()))
                .thenReturn(resposta);

        String json = """
                {
                  "quizId": "%s",
                  "respostas": [
                    {
                      "questaoId": 1,
                      "alternativa": "B"
                    }
                  ]
                }
                """.formatted(quizId);

        mockMvc.perform(
                        post("/api/quizzes/corrigir")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.acertos").value(1))
                .andExpect(jsonPath("$.percentualAcertos").value(100.0))
                .andExpect(jsonPath("$.resultados[0].acertou").value(true));
    }

    @Test
    void deveRejeitarAlternativaInvalida() throws Exception {

        String json = """
                {
                  "quizId": "%s",
                  "respostas": [
                    {
                      "questaoId": 1,
                      "alternativa": "E"
                    }
                  ]
                }
                """.formatted(UUID.randomUUID());

        mockMvc.perform(
                        post("/api/quizzes/corrigir")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar404QuandoQuizNaoExiste() throws Exception {

        UUID quizId = UUID.randomUUID();

        when(correcaoService.corrigir(any()))
                .thenThrow(
                        new QuizNaoEncontradoException(
                                "Quiz nao encontrado."
                        )
                );

        String json = """
                {
                  "quizId": "%s",
                  "respostas": [
                    {
                      "questaoId": 1,
                      "alternativa": "A"
                    }
                  ]
                }
                """.formatted(quizId);

        mockMvc.perform(
                        post("/api/quizzes/corrigir")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}

