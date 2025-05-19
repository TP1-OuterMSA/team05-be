package com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.dto;

import java.util.List;

public record QuizResponse(
        List<QuizDto> quizzes
) {
}
