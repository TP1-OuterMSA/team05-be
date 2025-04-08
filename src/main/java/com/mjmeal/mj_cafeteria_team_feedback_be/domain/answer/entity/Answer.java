package com.mjmeal.mj_cafeteria_team_feedback_be.domain.answer.entity;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.entity.BaseEntity;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.question.entity.Question;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Builder
    private Answer(String content, Review review, Question question) {
        this.content = content;
        this.review = review;
        this.question = question;
    }
}
