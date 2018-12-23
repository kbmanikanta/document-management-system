package rs.ac.bg.fon.silab.exception.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import rs.ac.bg.fon.silab.exception.ValidationException;
import rs.ac.bg.fon.silab.exception.EntityNotFoundException;
import rs.ac.bg.fon.silab.response.builder.ResponseBuilder;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ResponseBuilder responseBuilder;

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException e) {
        return responseBuilder.createResponse()
                .withStatus(HttpStatus.BAD_REQUEST)
                .withMessageKey("common.error.validation")
                .withErrors(e.getErrors())
                .build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        return responseBuilder.createResponse()
                .withStatus(HttpStatus.NOT_FOUND)
                .withMessageKey(e.getErrorMessageKey())
                .build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        return responseBuilder.createResponse()
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .withMessageKey("common.error.internal")
                .build();
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return responseBuilder.createResponse()
                .withStatus(status)
                .withMessage(ex.getMessage())
                .build();
    }

}
