package com.mjmeal.mj_cafeteria_team_feedback_be.domain.store.entity;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.entity.BaseEntity;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.store.StoreType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    @Column(nullable = false)
    private StoreType storeType;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String url;

    @Builder
    private Store(StoreType storeType, String name, String image, String description, String url) {
        this.storeType = storeType;
        this.name = name;
        this.image = image;
        this.description = description;
        this.url = url;
    }

    public void update(StoreType storeType, String name, String image, String description, String url) {
        this.storeType = storeType;
        this.name = name;
        this.image = image;
        this.description = description;
        this.url = url;
    }
}
