package com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserEarnRequest {
    private String phoneNumber;
    private BigDecimal point;
}
