package com.andela.irrigationsystem.exception;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.Map;

/**
 * @author brume
 */
@ControllerAdvice
@AllArgsConstructor
public class RestExceptionHandler {

    private final ErrorAttributes errorAttributes;

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValid(BadRequestException exception, WebRequest request) {

        Map<String, Object> errorAttributes = constructErrorResponse(HttpStatus.BAD_REQUEST, request, exception.getMessage());

        return ResponseEntity.badRequest().body(errorAttributes);
    }

    @ExceptionHandler({ElementNotFoundException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ElementNotFoundException exception, WebRequest request) {

        Map<String, Object> errorAttributes = constructErrorResponse(HttpStatus.NOT_FOUND, request, exception.getMessage());

        return ResponseEntity.badRequest().body(errorAttributes);
    }

    @ExceptionHandler({ElementWithSameIDAlreadyExistsException.class})
    public ResponseEntity<Object> handleElementWithSameIdException(ElementWithSameIDAlreadyExistsException exception, WebRequest request) {

        Map<String, Object> errorAttributes = constructErrorResponse(HttpStatus.NOT_FOUND, request, exception.getMessage());

        return ResponseEntity.badRequest().body(errorAttributes);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintException(ConstraintViolationException exception, WebRequest request) {

        Map<String, Object> errorAttributes = constructErrorResponse(HttpStatus.BAD_REQUEST, request, exception.getMessage());

        return ResponseEntity.badRequest().body(errorAttributes);
    }

    private Map<String, Object> constructErrorResponse(HttpStatus status, WebRequest request, String message) {
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        errorAttributes.put("status", status.value());
        errorAttributes.put("error", status.getReasonPhrase());
        errorAttributes.put("message", message);
        errorAttributes.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

        return errorAttributes;
    }
}