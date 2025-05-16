package com.mjmeal.mj_cafeteria_team_feedback_be.domain.question.repository;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.entity.Menu;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    boolean existsByMenu(Menu menu);
    List<Question> findByMenuIdIn(List<Long> menuIds);
    Question findByMenu(Menu menu);
}
