package com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.entity;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.entity.BaseEntity;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PredictRating extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal predict_rating;

    @Column(nullable = true)
    private BigDecimal actual_rating;

    @Column(nullable = false)
    private String mealDate;

    @OneToOne
    private User user;

    @Builder
    private PredictRating(BigDecimal predict_rating, BigDecimal actual_rating, String mealDate, User user) {
        this.predict_rating = predict_rating;
        this.actual_rating = actual_rating;
        this.mealDate = mealDate;
        this.user = user;
    }
}
