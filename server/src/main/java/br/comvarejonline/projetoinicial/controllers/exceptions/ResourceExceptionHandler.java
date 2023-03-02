package br.comvarejonline.projetoinicial.controllers.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.comvarejonline.projetoinicial.services.exceptions.ResourceNotFoundException;

/*
 * Classe que manipula as excesões no nível dos Controllers
 */

// Anotation que captura os erros nos Controllers
@ControllerAdvice
public class ResourceExceptionHandler {

    // Criando a estrutura do erro de recurso não encontrado
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(
            // Excesão customizada para recursos não encontrados
            ResourceNotFoundException e,
            // Objeto que possui as informações da requisição
            HttpServletRequest request) {

        // Definindo o status de retorno para 404
        HttpStatus status = HttpStatus.NOT_FOUND;

        // Montando o erro padrão
        StandardError standardError = new StandardError();
        standardError.setTimestamp(Instant.now());
        standardError.setStatus(status.value());
        standardError.setError("Entity not found");
        standardError.setMessage(e.getMessage());
        standardError.setPath(request.getRequestURI());

        // Retornando o erro customizado
        return ResponseEntity.status(status).body(standardError);
    }

    // Criando a estrutura do erros de validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(
            // Excesão de erros de validação
            MethodArgumentNotValidException e,
            // Objeto que possui as informações da requisição
            HttpServletRequest request) {

        // Definindo o status de retorno para 422
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        // Montando o erro de validação
        ValidationError validationError = new ValidationError();
        validationError.setTimestamp(Instant.now());
        validationError.setStatus(status.value());
        validationError.setError("Validation exception");
        validationError.setMessage(e.getMessage());
        validationError.setPath(request.getRequestURI());

        // Pegando os erros de validação e adicionando na lista
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            validationError.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        // Retornando o erro customizado
        return ResponseEntity.status(status).body(validationError);
    }

}
