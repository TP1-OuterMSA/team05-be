package com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.controller;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.response.ApiResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.RankingType;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.dto.*;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "유저")
@RestController
@RequestMapping("/api/team5/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "유저 정보 조회",
            description = """
        전화번호로 유저의 정보(번호, 총 포인트, 퀴즈 참가 가능 여부)를 조회합니다

        ✅ 요청 필드:
        - `phoneNumber`: 전화번호
        """
    )
    @PostMapping("/ensure")
    public ApiResponse<UserEnsureResponse> ensure(@RequestBody UserEnsureRequest userEnsureRequest) {
        return ApiResponse.onSuccess(userService.ensure(userEnsureRequest));
    }

    @Operation(
            summary = "퀴즈 정답자 포인트 +1",
            description = """
        퀴즈 정답자의 포인트를 1 증가시킵니다

        ✅ 요청 필드:
        - `phoneNumber`: 전화번호
        - `point`: 이전에 조회한 포인트에 1을 더한 값
        """
    )
    @PostMapping("/earn")
    public ApiResponse<UserEarnResponse> earn(@RequestBody UserEarnRequest userEarnRequest) {
        return ApiResponse.onSuccess(userService.earn(userEarnRequest));
    }

    @Operation(
            summary = "포인트 랭킹 조회 API.",
            description = """
        옵션별 사용자 포인트 랭킹을 조회합니다.

        ✅ 요청 필드:
        - `rankingType`: TOTAL, DAILY, WEEKLY, MONTHLY
        """
    )
    @GetMapping("/ranking")
    public ApiResponse<List<UserRankingResponse>> getRanking(@RequestParam("rankingType") RankingType rankingType) {
        return ApiResponse.onSuccess(userService.getRanking(rankingType));
    }

    @Operation(
            summary = "식단 평점 예측 정답자 조회 API",
            description = """
        식단의 평점을 예측 성공한 정답자를 조회합니다.

        ✅ 날짜 (`date`)는 아래와 같이 입력하세요:
        - ex) 2025-05-23
        """
    )
    @GetMapping("/ranking/{date}")
    public ApiResponse<List<UserRankingResponse>> getDateRanking(@PathVariable("date") LocalDate date) {
        return ApiResponse.onSuccess(userService.getDateRanking(date));
    }
}
