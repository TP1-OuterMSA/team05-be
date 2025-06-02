package com.mjmeal.mj_cafeteria_team_feedback_be.domain.user;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.MealType;
import lombok.Getter;

@Getter
public enum PointType {

    QUIZ("퀴즈"),
    PREDICT("별점 예측");

    private final String displayName;

    PointType(String displayName) {
        this.displayName = displayName;
    }

    public static PointType from(String value) {
        return PointType.valueOf(value.toUpperCase());
    }
}
