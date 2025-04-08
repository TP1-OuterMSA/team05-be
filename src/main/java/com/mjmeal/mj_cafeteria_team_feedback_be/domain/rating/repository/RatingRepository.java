package com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.repository;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
}
