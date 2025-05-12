package com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.dto;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.choice.dto.ChoiceDto;

import java.util.List;

public record QuizDto(
        Long id, String question,
        Long correctChoiceId,
        List<ChoiceDto> choiceDtoList) {
}
