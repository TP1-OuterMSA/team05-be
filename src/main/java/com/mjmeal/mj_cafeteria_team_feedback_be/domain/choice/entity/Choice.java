package com.mjmeal.mj_cafeteria_team_feedback_be.domain.choice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjmeal.mj_cafeteria_team_feedback_be.common.entity.BaseEntity;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.entity.Quiz;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Choice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Builder
    private Choice(String content, Quiz quiz) {
        this.content = content;
        this.quiz = quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}






