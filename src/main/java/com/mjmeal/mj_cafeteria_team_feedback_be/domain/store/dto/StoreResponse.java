package com.mjmeal.mj_cafeteria_team_feedback_be.domain.store.dto;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.store.entity.Store;
import java.util.List;

public record StoreResponse(
        List<Store> stores
    ) {
}
