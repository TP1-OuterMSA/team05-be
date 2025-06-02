package com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.entity.PredictRating;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.service.PredictedRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class RatingPredictionScheduler {

    private final PredictedRatingService predictedRatingService;

    @Scheduled(cron = "0 0 0 * * *")
    public void evaluatePredictedRatings() {
        LocalDate today = LocalDate.now().minusDays(1);
        String targetDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        predictedRatingService.evaluatePredictionsForDate(targetDate);
    }
}
