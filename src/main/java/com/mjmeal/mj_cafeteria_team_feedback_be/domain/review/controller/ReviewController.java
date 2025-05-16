package com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.controller;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.response.ApiResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.dto.ReviewRequest;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "리뷰")
@RestController
@RequestMapping("/api/team5/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(
            summary = "리뷰 저장",
            description = """
        식단에 대한 리뷰를 저장합니다.

        ✅ 요청 필드:
        - `mealId`: 식단 식별자 (필수)
        - `overallOpinion`: 전체 평점 (1~5점)
        - `menuRatings`: 메뉴 별점 목록 (필수)
        - `menuQuestions`: 메뉴별 질문
        - `menuAnswers` : 각 질문에 대한 답변
        - `freeOpinion` : 자유 의견

        ⚠️ `mealId`, `menuScores`는 필수입니다.
        """
    )
    @PostMapping
    public ApiResponse<Void> save(@RequestBody ReviewRequest request) {
        reviewService.save(request);
        return ApiResponse.onSuccess(null);
    }
}
