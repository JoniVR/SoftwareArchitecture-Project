package be.kdg.processor.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FineExceptionHandler extends ResponseEntityExceptionHandler {

    //@ExceptionHandler(value = {FineException.class})

}
