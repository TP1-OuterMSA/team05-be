package com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ReviewRequest {

    private Long mealId;
    private Map<String, Integer> menuRatings;
    private String overallOpinion;
    private Map<String, String> menuQuestions;
    private Map<String, String> menuAnswers;
    private String freeOpinion;

}
// 식단 식별값, 메뉴 별점, 전체 평점, 메뉴별 질문, 질문에 대한 답변, 지유의견을 받습니다
// 식단 식별값, 메뉴 별점은 필수값입니다.