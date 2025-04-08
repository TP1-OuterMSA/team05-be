package com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.entity;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.entity.BaseEntity;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.entity.Menu;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rating extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Builder
    private Rating(BigDecimal rating, Menu menu, Review review) {
        this.rating = rating;
        this.menu = menu;
        this.review = review;
    }
}
