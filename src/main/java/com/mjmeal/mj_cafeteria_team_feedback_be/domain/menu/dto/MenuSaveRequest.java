package com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MenuSaveRequest {
    private List<String> names;
}
