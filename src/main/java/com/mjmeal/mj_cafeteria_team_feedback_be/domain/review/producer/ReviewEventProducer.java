package com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.producer;

import com.example.kafka_schemas.ReviewEvent;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.entity.Meal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewEventProducer {

    private final KafkaTemplate<String, ReviewEvent> kafkaTemplate;

    public void sendReviewEvent(
            Meal meal,
            String overallOpinion,
            String freeOpinion,
            Map<String, Integer> menuRatings,
            Map<String, String> menuQuestions,
            Map<String, String> menuAnswers
    ) {
        ReviewEvent event = ReviewEvent.newBuilder()
                .setMealdayInfo(meal.getDayInfo())
                .setMealType(meal.getMealType().name())
                .setOverallOpinion(overallOpinion == null || overallOpinion.isBlank() ? null : overallOpinion)
                .setFreeOpinion(freeOpinion == null || freeOpinion.isBlank() ? null : freeOpinion)
                .setMenuRatings(menuRatings)
                .setMenuQuestions(menuQuestions)
                .setMenuAnswers(menuAnswers)
                .build();

        kafkaTemplate.send("review.events", event);
    }
}