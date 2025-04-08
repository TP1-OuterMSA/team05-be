package com.mjmeal.mj_cafeteria_team_feedback_be.common.response.error;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.response.code.StatusCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorStatus {

    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, StatusCode.COMMON.getCode(500), "서버 에러, 관리자에게 문의 바랍니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
