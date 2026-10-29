package com.otto.fitIA.exception;

public abstract class NegocioException extends RuntimeException {
    public NegocioException(String mensagem) {
        super(mensagem);
    }
}
