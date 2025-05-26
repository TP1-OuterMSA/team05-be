package com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.repository;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.entity.PredictRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredictRatingRepository extends JpaRepository<PredictRating, Long> {
    List<PredictRating> findByMealDate(String mealDate);
}
