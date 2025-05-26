package com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.controller;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.response.ApiResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.dto.RatingRequest;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "별점")
@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @Operation(
            summary = "식단 예측 API",
            description = """
        사용자가 식단을 평균 별점을 예측하는 API입니다.
       
        ✅ 요청 필드:
        - `rating`: 별점
        - `phoneNumber`: 전화번호
        - `date`: 조회할 일자
        """
    )
    @PostMapping("/predicted")
    public ApiResponse<Void> predict(@RequestBody RatingRequest request) {
        ratingService.predict(request);
        return ApiResponse.onSuccess(null);
    }
}
