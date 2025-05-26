package com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.repostiory;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.entity.UserPointLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserPointLogRepository extends JpaRepository<UserPointLog, Long> {

    @Query("""
    SELECT l.user.phoneNumber, SUM(l.point)
    FROM UserPointLog l
    WHERE l.createdAt >= :start AND l.createdAt < :end
    GROUP BY l.user.phoneNumber
    ORDER BY SUM(l.point) DESC
    LIMIT 7
""")
    List<Object[]> findTopEarnedByPeriod(@Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end);

    List<UserPointLog> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
