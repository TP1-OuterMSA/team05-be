package com.mjmeal.mj_cafeteria_team_feedback_be.domain.answer.repository;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
