package com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.controller;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.response.ApiResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.RankingType;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.dto.*;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "유저")
@RestController
@RequestMapping("/api/team5/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/ensure")
    public ApiResponse<UserEnsureResponse> ensure(@RequestBody UserEnsureRequest userEnsureRequest) {
        return ApiResponse.onSuccess(userService.ensure(userEnsureRequest));
    }

    @PostMapping("/earn")
    public ApiResponse<UserEarnResponse> earn(@RequestBody UserEarnRequest userEarnRequest) {
        return ApiResponse.onSuccess(userService.earn(userEarnRequest));
    }

    @GetMapping("/ranking")
    public ApiResponse<List<UserRankingResponse>> getRanking(@RequestParam("rankingType") RankingType rankingType) {
        return ApiResponse.onSuccess(userService.getRanking(rankingType));
    }
}
