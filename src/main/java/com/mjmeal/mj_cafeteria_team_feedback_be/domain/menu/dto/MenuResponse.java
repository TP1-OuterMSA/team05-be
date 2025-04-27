package com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.dto;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.entity.Menu;

import java.util.List;

public record MenuResponse(List<Menu> menuList) {
}
