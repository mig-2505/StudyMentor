package com.studymentor.studymentor.service;

import com.studymentor.studymentor.dto.CorrecaoRequest;
import com.studymentor.studymentor.dto.CorrecaoResponse;
import com.studymentor.studymentor.dto.RespostaAlunoDTO;
import com.studymentor.studymentor.exception.QuizNaoEncontradoException;
import com.studymentor.studymentor.model.QuestaoGeradaIA;
import com.studymentor.studymentor.model.QuizArmazenado;
import com.studymentor.studymentor.repository.QuizMemoryRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class CorrecaoService {

    private final QuizMemoryRepository repository;

    public CorrecaoService(QuizMemoryRepository repository) {
        this.repository = repository;
    }

    public CorrecaoResponse corrigir(CorrecaoRequest request) {

        // 1. Verificar a solicitacao
        if (request == null || request.quizId() == null) {
            throw new IllegalArgumentException(
                    "O ID do quiz e obrigatorio."
            );
        }

        UUID quizId = request.quizId();

        // 2. Buscar o quiz armazenado
        QuizArmazenado quiz = repository.buscarPorId(quizId)
                .orElseThrow(() -> new QuizNaoEncontradoException(
                        "Quiz nao encontrado: " + quizId
                ));

        List<QuestaoGeradaIA> questoes = quiz.questoes();

        // 3. Validar a quantidade de respostas
        List<RespostaAlunoDTO> respostas = request.respostas();

        if (respostas == null
                || respostas.size() != questoes.size()) {

            throw new IllegalArgumentException(
                    "Responda todas as questoes do quiz."
            );
        }

        // 4. Organizar as respostas por ID
        Map<Integer, String> respostasPorQuestao =
                new HashMap<>();

        Set<Integer> idsRespondidos = new HashSet<>();

        for (RespostaAlunoDTO resposta : respostas) {

            if (resposta == null
                    || resposta.questaoId() == null
                    || resposta.alternativa() == null) {

                throw new IllegalArgumentException(
                        "Existe uma resposta incompleta."
                );
            }

            int questaoId = resposta.questaoId();
            String alternativa = resposta.alternativa();

            if (questaoId < 1 || questaoId > questoes.size()) {
                throw new IllegalArgumentException(
                        "ID de questao invalido: " + questaoId
                );
            }

            if (!Set.of("A", "B", "C", "D")
                    .contains(alternativa)) {

                throw new IllegalArgumentException(
                        "Alternativa invalida na questao "
                                + questaoId
                );
            }

            if (!idsRespondidos.add(questaoId)) {
                throw new IllegalArgumentException(
                        "Questao respondida mais de uma vez: "
                                + questaoId
                );
            }

            respostasPorQuestao.put(questaoId, alternativa);
        }

        // 5. Corrigir as questoes
        int acertos = 0;

        List<CorrecaoResponse.ResultadoQuestaoDTO> resultados =
                new ArrayList<>();

        for (int i = 0; i < questoes.size(); i++) {

            int questaoId = i + 1;

            QuestaoGeradaIA questao = questoes.get(i);

            String respostaAluno =
                    respostasPorQuestao.get(questaoId);

            String respostaCorreta =
                    questao.respostaCorreta();

            boolean acertou =
                    respostaCorreta.equals(respostaAluno);

            if (acertou) {
                acertos++;
            }

            resultados.add(
                    new CorrecaoResponse.ResultadoQuestaoDTO(
                            questaoId,
                            respostaAluno,
                            respostaCorreta,
                            acertou,
                            questao.explicacao()
                    )
            );
        }

        // 6. Calcular a pontuacao
        int totalQuestoes = questoes.size();
        int erros = totalQuestoes - acertos;

        double percentualAcertos =
                (acertos * 100.0) / totalQuestoes;

        percentualAcertos =
                Math.round(percentualAcertos * 100.0) / 100.0;

        // 7. Retornar a correcao
        return new CorrecaoResponse(
                quizId,
                totalQuestoes,
                acertos,
                erros,
                percentualAcertos,
                List.copyOf(resultados)
        );
    }
}


