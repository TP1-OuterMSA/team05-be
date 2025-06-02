package com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;

@Getter
public class RatingRequest {
    private BigDecimal rating;
    private String phoneNumber;
    private LocalDate date;
}
