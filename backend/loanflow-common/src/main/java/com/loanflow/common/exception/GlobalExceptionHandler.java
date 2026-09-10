package com.loanflow.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LoanFlowException.class)
    public ProblemDetail handleLoanFlowException(LoanFlowException ex, HttpServletRequest request) {
        if (ex instanceof ResourceNotFoundException resourceNotFoundException) {
            return handleResourceNotFound(resourceNotFoundException, request);
        }
        if (ex instanceof DuplicateResourceException duplicateResourceException) {
            return handleDuplicateResource(duplicateResourceException, request);
        }
        if (ex instanceof ValidationException validationException) {
            return handleValidation(validationException, request);
        }
        if (ex instanceof ServiceUnavailableException serviceUnavailableException) {
            return handleServiceUnavailable(serviceUnavailableException, request);
        }

        return createProblemDetail(ex.getErrorCode(), ex.getMessage(), request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return createProblemDetail(ex.getErrorCode(), ex.getMessage(), request);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ProblemDetail handleDuplicateResource(DuplicateResourceException ex, HttpServletRequest request) {
        return createProblemDetail(ex.getErrorCode(), ex.getMessage(), request);
    }

    @ExceptionHandler(ValidationException.class)
    public ProblemDetail handleValidation(ValidationException ex, HttpServletRequest request) {
        return createProblemDetail(ex.getErrorCode(), ex.getMessage(), request);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ProblemDetail handleServiceUnavailable(ServiceUnavailableException ex, HttpServletRequest request) {
        return createProblemDetail(ex.getErrorCode(), ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = createProblemDetail(ErrorCode.VALIDATION_FAILED, "Validation failed", request);
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() == null ? "Invalid value" : fieldError.getDefaultMessage(),
                        (first, second) -> first,
                        LinkedHashMap::new
                ));
        problemDetail.setProperty("errors", fieldErrors);
        return problemDetail;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        String detail = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining(", "));
        return createProblemDetail(ErrorCode.VALIDATION_FAILED, detail, request);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception for request {}", request.getRequestURI(), ex);
        return createProblemDetail(ErrorCode.INTERNAL_ERROR, ex.getMessage(), request);
    }

    private ProblemDetail createProblemDetail(ErrorCode errorCode, String detail, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                errorCode.getHttpStatus(),
                detail
        );
        problemDetail.setTitle(errorCode.name());
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperty("errorCode", errorCode.name());
        return problemDetail;
    }
}
