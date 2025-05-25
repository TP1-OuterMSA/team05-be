package com.mjmeal.mj_cafeteria_team_feedback_be.domain.store.dto;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.store.StoreType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreRequest {
    private Long id;
    private StoreType storeType;
    private String name;
    private String description;
    private String url;
}
