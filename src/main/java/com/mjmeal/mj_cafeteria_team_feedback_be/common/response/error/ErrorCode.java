package com.mjmeal.mj_cafeteria_team_feedback_be.common.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "입력값이 올바르지 않습니다"),
    METHOD_NOT_ALLOWED(405, "C002", "지원하지 않는 HTTP 메서드입니다"),
    ENTITY_NOT_FOUND(404, "C003", "요청한 리소스를 찾을 수 없습니다"),
    INTERNAL_SERVER_ERROR(500, "C004", "서버 내부 오류가 발생했습니다"),
    INVALID_TYPE_VALUE(400, "C005", "유효하지 않은 타입입니다"),
    ACCESS_DENIED(403, "C006", "접근이 거부되었습니다"),
    ALREADY_EARNED_TODAY(400, "U001", "오늘은 이미 포인트를 적립했습니다."),

    // Member
    EMAIL_DUPLICATION(400, "M001", "이미 사용 중인 이메일입니다"),
    LOGIN_FAILED(401, "M002", "로그인에 실패했습니다"),

    // Business
    BUSINESS_EXCEPTION_ERROR(400, "B001", "비즈니스 요구사항 예외가 발생했습니다");

    private final int status;
    private final String code;
    private final String message;
}
