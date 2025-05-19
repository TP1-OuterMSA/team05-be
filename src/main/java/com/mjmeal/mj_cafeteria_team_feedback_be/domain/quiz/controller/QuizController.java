package com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.controller;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.response.ApiResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.choice.dto.ChoiceRequest;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.dto.QuizRequest;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.dto.QuizResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.service.QuizService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(name = "퀴즈")
@RestController
@RequestMapping("/api/team5/quizzes/admin")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @Operation(
            summary = "관리자 퀴즈 저장",
            description = """
        퀴즈 내용, 보기, 정답을 저장합니다.

        ✅ 요청 필드:
        - `question`: 퀴즈 내용
        - `choices`: 선택지 리스트
        - `correctIndex`: 정답 보기 index 0부터 시작
        """
    )
    @PostMapping
    public ApiResponse<Void> addQuiz(@RequestBody QuizRequest quizRequest) {
        quizService.addQuiz(quizRequest);
        return ApiResponse.onSuccess(null);
    }

    @Operation(
            summary = "관리자 선호 매뉴 목록 조회",
            description = """
        퀴즈 내용, 보기, 정답을 조회합니다.
        """
    )
    @GetMapping
    public ApiResponse<QuizResponse> getQuiz() {
        return ApiResponse.onSuccess(quizService.getQuiz());
    }

    @Operation(
            summary = "관리자 퀴즈 수정",
            description = """
        퀴즈 내용, 보기, 정답을 수정합니다.
        조회한 후 모든 값 재전송

        - `quizID`: 퀴즈 고유번호
        
        ✅ 요청 필드:
        - `question`: 퀴즈 내용
        - `choices`: 선택지 리스트
        - `correctIndex`: 정답 보기 index 0부터 시작
        """
    )
    @PutMapping("/{quizId}")
    public ApiResponse<Void> updateQuiz(
            @PathVariable("quizId") Long quizId,
            @RequestBody QuizRequest request
    ) {
        quizService.updateQuiz(quizId, request);
        return ApiResponse.onSuccess(null);
    }
}
