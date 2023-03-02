package br.comvarejonline.projetoinicial.services.exceptions;

/*
 * Excesão customizada para recursos não encontrados
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }

}