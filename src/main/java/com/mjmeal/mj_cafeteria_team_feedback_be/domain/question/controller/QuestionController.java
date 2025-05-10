package com.mjmeal.mj_cafeteria_team_feedback_be.domain.question.controller;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.response.ApiResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.question.dto.QuestionResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.question.service.QuestionService;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.MealType;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;


    @Operation(
            summary = "식단 질문 리스트 조회",
            description = """
        식단 질문 리스트를 조회합니다.

        ✅ 식사 유형 (`mealType`)은 아래 중 하나를 입력하세요:
        - `BREAKFAST`: 아침 식단
        - `LUNCH`: 점심 식단
        - `DINNER`: 저녁 식단

        🔄 응답 데이터:
        - 메뉴
        - 질문 내용
        """
    )
    @GetMapping
    public ApiResponse<QuestionResponse> getQuestionList(@RequestParam("mealType") MealType mealType){
        return ApiResponse.onSuccess(questionService.getQuestionList(mealType));
    }
}
