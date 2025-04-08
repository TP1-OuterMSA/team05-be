package com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.entity;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.entity.BaseEntity;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.MealType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String dayInfo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealType mealType;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL)
    private List<MealMenu> mealMenus;

    @Builder
    private Meal(String dayInfo, MealType mealType) {
        this.dayInfo = dayInfo;
        this.mealType = mealType;
    }
}
