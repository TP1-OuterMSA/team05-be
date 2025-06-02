package com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.repository;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.entity.Meal;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMealIdIn(List<Long> mealIds);

    List<Review> findByMeal(Meal meal);
}
