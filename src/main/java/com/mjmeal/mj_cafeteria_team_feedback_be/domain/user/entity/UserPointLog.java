package com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.entity;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.PointType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPointLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PointType pointType;

    @Column(nullable = false)
    private BigDecimal point;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    private UserPointLog(User user, PointType pointType, BigDecimal point, LocalDateTime createdAt) {
        this.user = user;
        this.pointType = pointType;
        this.point = point;
        this.createdAt = createdAt;
    }
}