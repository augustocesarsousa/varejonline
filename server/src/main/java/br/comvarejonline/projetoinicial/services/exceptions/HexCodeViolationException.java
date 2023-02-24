package br.comvarejonline.projetoinicial.services.exceptions;

public class HexCodeViolationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HexCodeViolationException(String message) {
        super(message);
    }

}
