package com.mjmeal.mj_cafeteria_team_feedback_be.common.exception;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.response.error.ErrorCode;

public class AccessDeniedException extends BusinessException {

    public AccessDeniedException() {
        super(ErrorCode.ACCESS_DENIED);
    }
}
