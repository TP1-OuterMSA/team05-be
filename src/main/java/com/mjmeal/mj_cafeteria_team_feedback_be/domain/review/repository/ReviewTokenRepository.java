package com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.repository;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.entity.ReviewToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewTokenRepository extends JpaRepository<ReviewToken, String> {
}
