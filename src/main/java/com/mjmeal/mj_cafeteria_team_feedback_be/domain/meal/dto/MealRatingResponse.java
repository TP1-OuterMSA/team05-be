package com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.dto;

import java.math.BigDecimal;

public record MealRatingResponse(
    String todayStr,
    BigDecimal average
  ) {
}
