package com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.service;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.dto.RatingRequest;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.entity.PredictRating;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.repository.PredictRatingRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.entity.User;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.repostiory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final UserRepository userRepository;
    private final PredictRatingRepository predictRatingRepository;

    @Transactional
    public void predict(RatingRequest request) {
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber());
        String today = request.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        PredictRating predictRating = PredictRating.builder()
                .predict_rating(request.getRating())
                .mealDate(today)
                .user(user)
                .build();

        predictRatingRepository.save(predictRating);
    }
}
