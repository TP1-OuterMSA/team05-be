package com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.service;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.dto.MealRatingResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.service.MealService;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.entity.PredictRating;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.repository.PredictRatingRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.PointType;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.entity.User;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.entity.UserPointLog;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.repostiory.UserPointLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PredictedRatingService {

    private final MealService mealService;
    private final PredictRatingRepository predictRatingRepository;
    private final UserPointLogRepository userPointLogRepository;

    @Transactional
    public void evaluatePredictionsForDate(String targetDate) {
        List<PredictRating> predictRatings = predictRatingRepository.findByMealDate(targetDate);
        MealRatingResponse response = mealService.getMenuRating(targetDate);

        BigDecimal actualAverage = response.average();

        for (PredictRating predictRating : predictRatings) {
            BigDecimal predicted = predictRating.getPredict_rating();

            int predictedRounded = predicted.setScale(1, RoundingMode.HALF_UP).multiply(BigDecimal.TEN).intValue();
            int actualRounded = actualAverage.setScale(1, RoundingMode.HALF_UP).multiply(BigDecimal.TEN).intValue();

            BigDecimal point = BigDecimal.valueOf((predictedRounded == actualRounded) ? 1 : 0);

            User user = predictRating.getUser();
            user.changePoint(user.getPoint().add(point));

            userPointLogRepository.save(
                    UserPointLog.builder()
                            .user(user)
                            .pointType(PointType.PREDICT)
                            .point(point)
                            .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                            .build()
            );
        }
    }
}
