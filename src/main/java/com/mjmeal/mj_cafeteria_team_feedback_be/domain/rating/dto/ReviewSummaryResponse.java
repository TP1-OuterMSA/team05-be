package com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReviewSummaryResponse {
    private long reviewCount;
    private double overallAverageRating;
    private List<String> overallOpinions;
}
