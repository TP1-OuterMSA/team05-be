package com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.repository;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.entity.MealMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealMenuRepository extends JpaRepository<MealMenu, Long> {
}
