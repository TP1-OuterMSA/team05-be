package com.mjmeal.mj_cafeteria_team_feedback_be.domain.review;

import lombok.Getter;

@Getter
public enum MealType {

    BREAKFAST("조식"),
    LUNCH("중식"),
    DINNER("석식");

    private final String displayName;

    MealType(String displayName) {
        this.displayName = displayName;
    }

    public static MealType from(String value) {
        return MealType.valueOf(value.toUpperCase());
    }
}