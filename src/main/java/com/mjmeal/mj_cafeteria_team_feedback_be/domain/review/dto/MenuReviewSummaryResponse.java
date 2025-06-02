package com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MenuReviewSummaryResponse {
    private String menuName;
    private double averageRating;
    private String aiQuestion;
    private List<String> userResponses;
}