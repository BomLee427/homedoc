package bom.proj.homedoc.exception;

import bom.proj.homedoc.dto.response.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import javax.persistence.EntityNotFoundException;
import java.util.Map;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
// TODO: API 자체 예외면 커스텀 익셉션을 만드는게 낫나?
    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildResponseEntity(
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("Malformed JSON request")
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildResponseEntity(
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("Parameter is not valid")
                        .debugMessage(ex.getBindingResult().getFieldErrors().stream().map(e -> Map.of(e.getField(), e.getDefaultMessage())).collect(Collectors.toList()).toString())
                        .build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        return buildResponseEntity(ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    protected ResponseEntity<Object> handleDuplicateKey(DuplicateKeyException ex) {
        return buildResponseEntity(ErrorResponse.builder()
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    protected ResponseEntity<Object> handleNoResourceFound(NoResourceFoundException ex) {
        return buildResponseEntity(ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .build());
    }
}
