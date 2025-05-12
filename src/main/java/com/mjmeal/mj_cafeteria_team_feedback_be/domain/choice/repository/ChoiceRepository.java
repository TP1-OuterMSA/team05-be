package com.mjmeal.mj_cafeteria_team_feedback_be.domain.choice.repository;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.choice.entity.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}
