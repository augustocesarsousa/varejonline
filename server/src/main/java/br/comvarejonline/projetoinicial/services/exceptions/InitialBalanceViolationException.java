package br.comvarejonline.projetoinicial.services.exceptions;

/**
 * InitialBalanceViolationException
 */
public class InitialBalanceViolationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InitialBalanceViolationException(String message) {
        super(message);
    }

}