package com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.service;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.exception.BusinessException;
import com.mjmeal.mj_cafeteria_team_feedback_be.common.response.error.ErrorCode;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.PointType;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.RankingType;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.dto.*;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.entity.User;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.entity.UserPointLog;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.repostiory.UserPointLogRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.repostiory.UserRepository;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserPointLogRepository userPointLogRepository;
    private final UserRepository userRepository;

    @Transactional
    public UserEnsureResponse ensure(UserEnsureRequest userEnsureRequest) {
        String phone = userEnsureRequest.getPhoneNumber();
        User user = userRepository.findByPhoneNumber(phone);

        if (user == null) {
            user = User.builder()
                    .phoneNumber(phone)
                    .point(BigDecimal.ZERO)
                    .lastEarnDate(null)
                    .build();
            userRepository.save(user);
        }

        String todayStr = LocalDate.now(ZoneId.of("Asia/Seoul"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        boolean canEarnToday = user.getLastEarnDate() == null ||
                !user.getLastEarnDate().equals(todayStr);


        return new UserEnsureResponse(user.getPhoneNumber(), user.getPoint(), canEarnToday);
    }

    @Transactional
    public UserEarnResponse earn(UserEarnRequest userEarnRequest) {
        User user = userRepository.findByPhoneNumber(userEarnRequest.getPhoneNumber());

        String lastEarnDate = LocalDate.now(ZoneId.of("Asia/Seoul"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (user.getLastEarnDate() != null && user.getLastEarnDate().equals(lastEarnDate)) {
            throw new BusinessException(ErrorCode.ALREADY_EARNED_TODAY);
        }

        userPointLogRepository.save(
                UserPointLog.builder()
                        .user(user)
                        .pointType(PointType.QUIZ)
                        .point(userEarnRequest.getPoint())
                        .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                        .build()
        );

        user.changePoint(userEarnRequest.getPoint());
        user.setLastEarnDate(lastEarnDate);
        return new UserEarnResponse(user.getPhoneNumber(), user.getPoint());
    }

    @Transactional(readOnly = true)
    public List<UserRankingResponse> getRanking(RankingType rankingType) {
        List<Object[]> result;

        LocalDateTime start;
        LocalDateTime end;

        ZoneId zone = ZoneId.of("Asia/Seoul");
        LocalDate today = LocalDate.now(zone);

        switch (rankingType) {
            case DAILY -> {
                start = today.atStartOfDay();
                end = start.plusDays(1);
                result = userPointLogRepository.findTopEarnedByPeriod(start, end);
            }
            case WEEKLY -> {
                start = today.with(java.time.DayOfWeek.MONDAY).atStartOfDay();
                end = start.plusDays(7);
                result = userPointLogRepository.findTopEarnedByPeriod(start, end);
            }
            case MONTHLY -> {
                start = today.withDayOfMonth(1).atStartOfDay();
                end = today.withDayOfMonth(1).plusMonths(1).atStartOfDay(); // ✅ 수정
                result = userPointLogRepository.findTopEarnedByPeriod(start, end);
            }
            case TOTAL -> {
                return userRepository.findTop7ByOrderByPointDesc().stream()
                        .map(u -> new UserRankingResponse(u.getPhoneNumber(), u.getPoint()))
                        .toList();
            }
            default -> throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }

        return result.stream()
                .map(r -> new UserRankingResponse((String) r[0], (BigDecimal) r[1]))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserRankingResponse> getDateRanking(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);
        List<UserPointLog> logs = userPointLogRepository.findByCreatedAtBetween(start, end);

        return logs.stream()
                .filter(log -> log.getPointType() == PointType.PREDICT)
                .map(log -> new UserRankingResponse(
                        log.getUser().getPhoneNumber(),
                        log.getUser().getPoint()
                ))
                .toList();
    }
}
