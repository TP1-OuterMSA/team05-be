package com.mjmeal.mj_cafeteria_team_feedback_be.domain.choice.repository;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.choice.entity.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
    List<Choice> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
