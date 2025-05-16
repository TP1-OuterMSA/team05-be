package com.mjmeal.mj_cafeteria_team_feedback_be.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.mjmeal.mj_cafeteria_team_feedback_be.common.response.error.ErrorCode;
import com.mjmeal.mj_cafeteria_team_feedback_be.common.response.success.SuccessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    private final Boolean isSuccess;
    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public static <T> ApiResponse<T> onSuccess(T result){
        return new ApiResponse<>(true, SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), result);
    }

    public static <T> ApiResponse<T> onSuccess(SuccessStatus status, T result) {
        return new ApiResponse<>(true, status.getCode(), status.getMessage(), result);
    }

    public static <T> ApiResponse<T> onFailure(T result){
        return new ApiResponse<>(false, ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), result);
    }

    public static <T> ApiResponse<T> onFailure(ErrorCode status, T result){
        return new ApiResponse<>(false, status.getCode(), status.getMessage(), result);
    }
}
