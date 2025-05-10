package com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.dto;

import java.math.BigDecimal;

public record UserEnsureResponse(
        String phoneNumber,
        BigDecimal point,
        boolean canEarnToday
) {
}
