package com.studymentor.studymentor.exception;

public class QuizNaoEncontradoException
        extends RuntimeException {

    public QuizNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}

