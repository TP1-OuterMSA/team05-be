package com.mjmeal.mj_cafeteria_team_feedback_be.domain.question.dto;

import java.util.List;

public record QuestionResponse(
        List<QuestionItemDto> questions
) {
}
