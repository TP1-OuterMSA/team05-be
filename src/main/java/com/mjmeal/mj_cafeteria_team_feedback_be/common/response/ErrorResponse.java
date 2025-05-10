package com.mjmeal.mj_cafeteria_team_feedback_be.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mjmeal.mj_cafeteria_team_feedback_be.common.exception.BusinessException;
import com.mjmeal.mj_cafeteria_team_feedback_be.common.response.error.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<FieldError> errors;

    private ErrorResponse(ErrorCode errorCode) {
        this.timestamp = LocalDateTime.now();
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.errors = new ArrayList<>();
    }

    private ErrorResponse(ErrorCode errorCode, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = message;
        this.errors = new ArrayList<>();
    }

    private ErrorResponse(ErrorCode errorCode, List<FieldError> errors) {
        this.timestamp = LocalDateTime.now();
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.errors = errors;
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return new ErrorResponse(errorCode, message);
    }

    public static ErrorResponse of(BusinessException e) {
        return new ErrorResponse(e.getErrorCode());
    }

    public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
        return new ErrorResponse(errorCode, FieldError.of(bindingResult));
    }

    public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());
        return new ErrorResponse(ErrorCode.INVALID_TYPE_VALUE, errors);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(String field, String value, String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        public static List<FieldError> of(BindingResult bindingResult) {
            List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}
