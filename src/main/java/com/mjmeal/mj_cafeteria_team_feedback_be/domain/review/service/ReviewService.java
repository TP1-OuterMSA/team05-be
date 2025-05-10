package com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.service;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.answer.entity.Answer;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.answer.repository.AnswerRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.entity.Meal;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.repository.MealRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.entity.Menu;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.repository.MenuRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.question.entity.Question;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.question.repository.QuestionRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.entity.Rating;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.repository.RatingRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.dto.ReviewRequest;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.entity.Review;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MealRepository mealRepository;
    private final MenuRepository menuRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final RatingRepository ratingRepository;

    @Transactional
    public void save(ReviewRequest request) {
        Meal meal = mealRepository.findById(request.getMealId()).orElseThrow();

        Review review = Review.builder()
                .overallOpinion(request.getOverallOpinion().isBlank() ? null : request.getOverallOpinion())
                .freeOpinion(request.getFreeOpinion().isBlank() ? null : request.getFreeOpinion())
                .meal(meal)
                .build();

        reviewRepository.save(review);

        for (Map.Entry<String, Integer> entry : request.getMenuRatings().entrySet()) {
            String menuName = entry.getKey();
            Menu menu = menuRepository.findByName(menuName);

            Rating rating = Rating.builder()
                    .rating(BigDecimal.valueOf(entry.getValue()))
                    .menu(menu)
                    .review(review)
                    .build();

            ratingRepository.save(rating);
        }

        Map<String, String> menuQuestions = request.getMenuQuestions();
        Map<String, String> menuAnswers = request.getMenuAnswers();
        if (menuQuestions != null) {
            for (Map.Entry<String, String> questionEntry : menuQuestions.entrySet()) {
                if (!StringUtils.hasText(questionEntry.getValue())) {
                    continue;
                }

                String menuName = questionEntry.getKey();
                String questionContent = questionEntry.getValue();
                Menu menu = menuRepository.findByName(menuName);

                boolean hasQuestion = questionRepository.existsByMenu(menu);
                Question question;

                if (!hasQuestion) {
                    question = Question.builder()
                            .content(questionContent)
                            .menu(menu)
                            .build();
                    questionRepository.save(question);
                } else {
                    question = questionRepository.findByMenu(menu);
                }

                if (menuAnswers != null && StringUtils.hasText(menuAnswers.get(menuName))) {
                    Answer answer = Answer.builder()
                            .content(menuAnswers.get(menuName))
                            .review(review)
                            .question(question)
                            .build();
                    answerRepository.save(answer);
                }
            }
        }
    }
}
