package br.comvarejonline.projetoinicial.services.exceptions;

public class MovementValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MovementValidationException(String message) {
        super(message);
    }

}
