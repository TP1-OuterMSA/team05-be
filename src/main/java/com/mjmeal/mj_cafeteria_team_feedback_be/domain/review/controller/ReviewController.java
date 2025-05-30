package com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.controller;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.response.ApiResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.dto.ReviewRequest;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        - 'token': 프론트에서 생성한 리뷰 등록할때 필요한 고유값
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

    @Operation(
            summary = "리뷰 토큰 사용가능 여부",
            description = """
        프론트에서 넘기는 리뷰토큰의 사용가능 여부를 조회합니다. ex) url
        """
    )
    @GetMapping("/token/{token}")
    private ApiResponse<Boolean> getUseAble(@PathVariable("token") String token) {
        return ApiResponse.onSuccess(reviewService.getUseAble(token));
    }

    @Operation(
            summary = "리뷰 토큰 등록 테스트",
            description = """
        리뷰 없이 토큰만 등록하여, 이후 해당 토큰으로 요청 시
        사용 여부(true/false) 응답을 확인하기 위한 테스트용 API입니다.
    """
    )
    @GetMapping("/check/{token}")
    private void registerToken(@PathVariable("token") String token) {
        reviewService.registerToken(token);
    }

}
