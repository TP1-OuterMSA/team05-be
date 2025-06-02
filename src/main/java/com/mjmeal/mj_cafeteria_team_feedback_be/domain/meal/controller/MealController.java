package com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.controller;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.response.ApiResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.dto.MealRatingResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.dto.MealResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.service.MealService;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.MealType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "식단")
@RestController
@RequestMapping("/api/team5/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @Operation(
            summary = "오늘의 식단 조회",
            description = """
        오늘의 식단 정보를 조회합니다.

        ✅ 식사 유형 (`mealType`)은 아래 중 하나를 입력하세요:
        - `LUNCH`: 점심 식단
        - `DINNER`: 저녁 식단

        🔄 응답 데이터:
        - 식단 ID
        - 식단 날짜 (yyyy-MM-dd)
        - 식사 유형 (LUNCH 또는 DINNER)
        - 메뉴 목록 (String 리스트)
        """
    )
    @GetMapping("/today")
    public ApiResponse<MealResponse> getTodayMenu(@RequestParam("mealType") MealType mealType) {
        return ApiResponse.onSuccess(mealService.findMealByDateAndMealType(mealType));
    }

    @Operation(
        summary = "식단 평균 평점 조회",
        description = """
        식단의 평균 평점을 조회합니다.

        ✅ 날짜 (`date`)는 아래와 같이 입력하세요:
        - ex) 2025-04-15

        🔄 응답 데이터:
        - 식단 날짜 (yyyy-MM-dd)
        - 평균 평점
        """
    )
    @GetMapping("/menus/rating/{date}")
    public ApiResponse<MealRatingResponse> getMealRating(@PathVariable("date") LocalDate date) {
        String today = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return ApiResponse.onSuccess(mealService.getMenuRating(today));
    }
}
