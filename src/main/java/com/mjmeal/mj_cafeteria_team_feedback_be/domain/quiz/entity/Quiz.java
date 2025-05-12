package com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.entity;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.entity.BaseEntity;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.choice.entity.Choice;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Choice> choices = new ArrayList<>();

    @Column(nullable = false)
    private Long correctChoiceId = 0L;

    @Builder
    private Quiz(String question, Long correctChoiceId) {
        this.question = question;
    }

    public void addChoice(Choice choice) {
        choice.setQuiz(this);
        choices.add(choice);
    }

    public void setCorrectChoiceId(Long correctChoiceId) {
        this.correctChoiceId = correctChoiceId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}