package com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.dto;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.MealType;

import java.util.List;

public record MealResponse(
        Long id,
        String dayInfo,
        MealType mealType,
        List<String> menuNames
    ) {
}
