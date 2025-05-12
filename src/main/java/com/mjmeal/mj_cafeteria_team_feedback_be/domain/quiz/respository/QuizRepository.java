package com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.respository;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
