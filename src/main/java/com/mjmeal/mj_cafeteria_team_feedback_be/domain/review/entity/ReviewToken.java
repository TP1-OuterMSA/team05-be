package com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.entity;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewToken extends BaseEntity {

    @Id
    private String token;

    public static ReviewToken from(String token) {
        return new ReviewToken(token);
    }
}
