package com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.entity;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private BigDecimal point;

    @Column(nullable = true)
    private String lastEarnDate;

    @Builder
    private User(String phoneNumber, BigDecimal point, String lastEarnDate) {
        this.phoneNumber = phoneNumber;
        this.point = point;
        this.lastEarnDate = lastEarnDate;
    }

    public void changePoint(BigDecimal point) {
        this.point = point;
    }

    public void setLastEarnDate(String lastEarnDate) {
        this.lastEarnDate = lastEarnDate;
    }
}
