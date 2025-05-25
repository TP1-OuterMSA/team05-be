package com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.repository;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.entity.Meal;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.MealType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    Meal findByDayInfoStartingWithAndMealType(String dayInfoPrefix, MealType mealType);

    List<Meal> findByDayInfo(String todayStr);
}
