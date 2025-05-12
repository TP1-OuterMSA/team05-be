package com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.dto;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.choice.dto.ChoiceRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class QuizRequest {
    private String question;
    private List<ChoiceRequest> choices;
    private int correctIndex;
}
