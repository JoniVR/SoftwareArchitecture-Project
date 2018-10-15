package be.kdg.processor.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FineExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {FineException.class})
    protected ResponseEntity<?> handleFineNotFound(RuntimeException ex, WebRequest request) {

        return handleExceptionInternal(ex, "Fine not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
