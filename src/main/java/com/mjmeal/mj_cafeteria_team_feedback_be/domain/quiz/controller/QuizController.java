package com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.controller;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.response.ApiResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.dto.QuizRequest;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.dto.QuizResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team5/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping
    public ApiResponse<Void> addQuiz(@RequestBody QuizRequest quizRequest) {
        quizService.addQuiz(quizRequest);
        return ApiResponse.onSuccess(null);
    }

    @GetMapping
    public ApiResponse<QuizResponse> getQuiz() {
        return ApiResponse.onSuccess(quizService.getQuiz());
    }
}
