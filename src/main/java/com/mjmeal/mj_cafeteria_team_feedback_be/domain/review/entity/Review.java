package com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.entity;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.entity.BaseEntity;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.entity.Meal;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String overallOpinion;

    private String freeOpinion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @Builder
    private Review(String overallOpinion, String freeOpinion, Meal meal) {
        this.overallOpinion = overallOpinion;
        this.freeOpinion = freeOpinion;
        this.meal = meal;
    }
}
