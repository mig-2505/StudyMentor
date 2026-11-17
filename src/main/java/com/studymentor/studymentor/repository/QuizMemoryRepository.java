package com.studymentor.studymentor.repository;

import com.studymentor.studymentor.model.QuizArmazenado;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class QuizMemoryRepository {

    private final Map<UUID, QuizArmazenado> quizzes = new ConcurrentHashMap<>();

    public void salvar(QuizArmazenado quiz) {
        quizzes.put(quiz.id(), quiz);
    }

    public Optional<QuizArmazenado> buscarPorId(UUID id) {
        return Optional.ofNullable(quizzes.get(id));
    }
}