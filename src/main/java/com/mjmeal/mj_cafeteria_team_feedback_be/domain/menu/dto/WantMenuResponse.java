package com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.dto;

import java.util.List;

public record WantMenuResponse(
        List<WantMenuDto> wantMenus
) {
}
