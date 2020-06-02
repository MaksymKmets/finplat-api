package com.flax.finplat.exception;

import com.flax.finplat.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class handles all the exceptions on the REST level
 */
@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String TYPE_MISMATCH_ERROR_CODE = "typeMismatch";
    private static final String WRONG_TYPE_MESSAGE = "Wrong type provided";
    public static final String INTERNAL_SERVER_ERROR_CHECK_LOGS_FOR_DETAILS = "Internal server error. Check logs for details";
    public static final String INVALID_INPUT = "INVALID_INPUT";

    /**
     * General exception handling
     *
     * @param e
     * @return
     */
    @ExceptionHandler(FinplatException.class)
    protected ResponseEntity<Object> handleFinplatException(FinplatException e) {
        logError(e);
        return buildResponseEntity(e.getStatus(), e.getCode(), e.getMessage());
    }

    /**
     * Javax.bean.validation exception handling
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        logError(e);
        return buildResponseEntity(HttpStatus.BAD_REQUEST,
                                   INVALID_INPUT,
                                   e.getConstraintViolations().stream()
                                           .map(ConstraintViolation::getMessage)
                                           .collect(Collectors.joining("\n")));
    }


    @ExceptionHandler(ServerWebInputException.class)
    protected ResponseEntity<Object> handleInvalidControllerInput(ServerWebInputException e) {
        logError(e);
        return buildResponseEntity(HttpStatus.BAD_REQUEST, INVALID_INPUT, e.getMessage());
    }

    @ExceptionHandler(WebExchangeBindException.class)
    protected ResponseEntity<Object> handleParamValidationException(WebExchangeBindException exception) {
        logError(exception);
        return bindingErrorResult(exception);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        logError(exception);
        return bindingErrorResult(exception.getBindingResult());
    }

    /**
     * Handling of the unknown errors
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleUnknownException(Exception e) {
        logError(e);
        return buildResponseEntity(HttpStatus.BAD_REQUEST,
                                   INVALID_INPUT,
                                   INTERNAL_SERVER_ERROR_CHECK_LOGS_FOR_DETAILS);
    }

    private ResponseEntity<Object> bindingErrorResult(BindingResult bindingResult) {
        String message = bindingResult.getAllErrors().stream()
                .map(e -> String.format("%s: %s",
                                        getErrorTargetName(e),
                                        getErrorMessage(e)))
                .collect(Collectors.joining("; "));
        return buildResponseEntity(HttpStatus.BAD_REQUEST, INVALID_INPUT, message);
    }

    private MultiValueMap<String, String> createHeaders() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        return headers;
    }

    private String getErrorTargetName(ObjectError error) {
        return error instanceof FieldError
                ? ((FieldError) error).getField()
                : error.getObjectName();
    }

    private String getErrorMessage(ObjectError error) {
        String[] codes = Optional.ofNullable(error.getCodes())
                .orElse(new String[]{});

        return Arrays.stream(codes)
                .filter(e -> Objects.equals(e, TYPE_MISMATCH_ERROR_CODE))
                .findAny()
                .map(e -> WRONG_TYPE_MESSAGE)
                .orElseGet(error::getDefaultMessage);
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status,
                                                       String code,
                                                       String message) {
        ErrorResponse.ApiError error = ErrorResponse.ApiError.valueOf(code, message);
        ErrorResponse exceptionResponse = ErrorResponse.valueOf(Collections.singletonList(error));
        return new ResponseEntity<>(exceptionResponse, createHeaders(), status);
    }

    private void logError(Throwable e) {
        log.info("Error: {}", e.getMessage(), e);
    }
}
