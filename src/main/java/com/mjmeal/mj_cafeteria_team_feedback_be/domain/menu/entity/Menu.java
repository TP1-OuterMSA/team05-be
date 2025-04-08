package com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.entity;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Builder
    private Menu(String name) {
        this.name = name;
    }
}
