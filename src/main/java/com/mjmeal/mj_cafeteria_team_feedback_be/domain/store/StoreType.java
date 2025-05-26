package com.mjmeal.mj_cafeteria_team_feedback_be.domain.store;

import lombok.Getter;

@Getter
public enum StoreType {

    RESTAURANT("음식점"),
    CAFE("카페");

    public final String name;

    StoreType(String name) {
        this.name = name;
    }

    public static StoreType from(String value) {
        return StoreType.valueOf(value.toUpperCase());
    }
}
