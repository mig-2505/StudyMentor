package com.studymentor.studymentor.service;

import com.studymentor.studymentor.dto.CorrecaoRequest;
import com.studymentor.studymentor.dto.CorrecaoResponse;
import com.studymentor.studymentor.dto.RespostaAlunoDTO;
import com.studymentor.studymentor.exception.QuizNaoEncontradoException;
import com.studymentor.studymentor.model.QuestaoGeradaIA;
import com.studymentor.studymentor.model.QuizArmazenado;
import com.studymentor.studymentor.repository.QuizMemoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CorrecaoServiceTest {

    @Mock
    private QuizMemoryRepository repository;

    @InjectMocks
    private CorrecaoService correcaoService;

    private UUID quizId;
    private QuizArmazenado quiz;

    @BeforeEach
    void preparar() {

        quizId = UUID.randomUUID();

        QuestaoGeradaIA questao1 = new QuestaoGeradaIA(
                "Qual palavra-chave declara uma classe?",
                Map.of(
                        "A", "new",
                        "B", "class",
                        "C", "void",
                        "D", "return"
                ),
                "B",
                "class declara uma classe em Java."
        );

        QuestaoGeradaIA questao2 = new QuestaoGeradaIA(
                "Qual tipo representa verdadeiro ou falso?",
                Map.of(
                        "A", "String",
                        "B", "int",
                        "C", "boolean",
                        "D", "double"
                ),
                "C",
                "boolean representa true ou false."
        );

        QuestaoGeradaIA questao3 = new QuestaoGeradaIA(
                "Qual conceito controla acesso aos dados?",
                Map.of(
                        "A", "Encapsulamento",
                        "B", "Heranca",
                        "C", "Polimorfismo",
                        "D", "Sobrecarga"
                ),
                "A",
                "Encapsulamento controla o acesso aos dados."
        );

        quiz = new QuizArmazenado(
                quizId,
                "Java",
                "FACIL",
                List.of(questao1, questao2, questao3)
        );
    }

    @Test
    void deveCorrigirTodasAsRespostasCertas() {

        when(repository.buscarPorId(quizId))
                .thenReturn(Optional.of(quiz));

        CorrecaoRequest request = new CorrecaoRequest(
                quizId,
                List.of(
                        new RespostaAlunoDTO(1, "B"),
                        new RespostaAlunoDTO(2, "C"),
                        new RespostaAlunoDTO(3, "A")
                )
        );

        CorrecaoResponse resultado =
                correcaoService.corrigir(request);

        assertEquals(3, resultado.totalQuestoes());
        assertEquals(3, resultado.acertos());
        assertEquals(0, resultado.erros());
        assertEquals(100.0, resultado.percentualAcertos());
        assertEquals(3, resultado.resultados().size());
        assertTrue(resultado.resultados().get(0).acertou());
    }

    @Test
    void deveCalcularNotaComUmaRespostaErrada() {

        when(repository.buscarPorId(quizId))
                .thenReturn(Optional.of(quiz));

        CorrecaoRequest request = new CorrecaoRequest(
                quizId,
                List.of(
                        new RespostaAlunoDTO(1, "B"),
                        new RespostaAlunoDTO(2, "A"),
                        new RespostaAlunoDTO(3, "A")
                )
        );

        CorrecaoResponse resultado =
                correcaoService.corrigir(request);

        assertEquals(2, resultado.acertos());
        assertEquals(1, resultado.erros());
        assertEquals(66.67, resultado.percentualAcertos());
        assertFalse(resultado.resultados().get(1).acertou());
    }

    @Test
    void deveRejeitarQuizInexistente() {

        when(repository.buscarPorId(quizId))
                .thenReturn(Optional.empty());

        CorrecaoRequest request = new CorrecaoRequest(
                quizId,
                List.of(new RespostaAlunoDTO(1, "A"))
        );

        assertThrows(
                QuizNaoEncontradoException.class,
                () -> correcaoService.corrigir(request)
        );
    }

    @Test
    void deveRejeitarRespostasDuplicadas() {

        when(repository.buscarPorId(quizId))
                .thenReturn(Optional.of(quiz));

        CorrecaoRequest request = new CorrecaoRequest(
                quizId,
                List.of(
                        new RespostaAlunoDTO(1, "A"),
                        new RespostaAlunoDTO(1, "B"),
                        new RespostaAlunoDTO(3, "C")
                )
        );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> correcaoService.corrigir(request)
                );

        assertTrue(
                exception.getMessage().contains(
                        "Questao respondida mais de uma vez"
                )
        );
    }

    @Test
    void deveRejeitarRespostasFaltantes() {

        when(repository.buscarPorId(quizId))
                .thenReturn(Optional.of(quiz));

        CorrecaoRequest request = new CorrecaoRequest(
                quizId,
                List.of(
                        new RespostaAlunoDTO(1, "B"),
                        new RespostaAlunoDTO(2, "C")
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> correcaoService.corrigir(request)
        );
    }
}

