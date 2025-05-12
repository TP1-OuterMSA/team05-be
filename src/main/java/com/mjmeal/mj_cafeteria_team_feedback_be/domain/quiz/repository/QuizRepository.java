package com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.repository;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
