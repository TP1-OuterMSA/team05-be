package com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.repository;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    Menu findByName(String name);
    List<Menu> findTop8ByOrderByIdDesc();

}
