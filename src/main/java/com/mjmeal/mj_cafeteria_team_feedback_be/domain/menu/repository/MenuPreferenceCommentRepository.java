package com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.repository;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.entity.MenuPreferenceComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuPreferenceCommentRepository extends JpaRepository<MenuPreferenceComment, Long> {
}
