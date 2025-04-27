package com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuRequest {
    private List<String> menuNames;
    private String comment;
}
