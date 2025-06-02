package com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ReviewRequest {

    private String token;
    private Long mealId;
    private Map<String, Integer> menuRatings;
    private String overallOpinion;
    private Map<String, String> menuQuestions;
    private Map<String, String> menuAnswers;
    private String freeOpinion;

}
