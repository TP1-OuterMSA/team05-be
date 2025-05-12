package com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.repository;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.entity.WantMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WantMenuRepository extends JpaRepository<WantMenu, Long> {
}
