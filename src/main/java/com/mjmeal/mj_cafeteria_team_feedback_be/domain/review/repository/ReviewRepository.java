package com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.repository;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
