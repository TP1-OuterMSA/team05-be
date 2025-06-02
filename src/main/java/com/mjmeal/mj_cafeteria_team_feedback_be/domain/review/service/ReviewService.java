package com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.service;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.exception.BusinessException;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.answer.entity.Answer;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.answer.repository.AnswerRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.entity.Meal;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.entity.MealMenu;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.repository.MealMenuRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.repository.MealRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.entity.Menu;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.repository.MenuRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.question.entity.Question;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.question.repository.QuestionRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.dto.ReviewSummaryResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.entity.Rating;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.repository.RatingRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.MealType;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.dto.MenuReviewSummaryResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.dto.ReviewRequest;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.entity.Review;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.entity.ReviewToken;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.repository.ReviewRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.repository.ReviewTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.mjmeal.mj_cafeteria_team_feedback_be.common.response.error.ErrorCode.ALREADY_TOKEN;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MealRepository mealRepository;
    private final MenuRepository menuRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final RatingRepository ratingRepository;
    private final ReviewTokenRepository reviewTokenRepository;
    private final MealMenuRepository mealMenuRepository;

    @Transactional
    public void save(ReviewRequest request) {
        if (reviewTokenRepository.findById(request.getToken()).isPresent()) {
            throw new BusinessException(ALREADY_TOKEN);
        }

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
        reviewTokenRepository.save(ReviewToken.from(request.getToken()));
    }

    public Boolean getUseAble(String token) {
        if (reviewTokenRepository.findById(token).isPresent()) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    @Transactional
    public void registerToken(String token) {
        reviewTokenRepository.save(ReviewToken.from(token));
    }

    @Transactional(readOnly = true)
    public ReviewSummaryResponse getReviewSummary(MealType mealType) {

        List<Meal> mealList = mealRepository.findByMealType(mealType);
        List<Long> mealIds = mealList.stream()
                .map(Meal::getId)
                .toList();

        List<Review> reviews = reviewRepository.findByMealIdIn(mealIds);
        long reviewCount = reviews.size();

        double overallAverageRating = 0.0;
        if (!reviews.isEmpty()) {
            overallAverageRating = reviews.stream()
                    .map(review -> {
                        List<Rating> ratings = ratingRepository.findByReview(review);
                        return ratings.stream()
                                .map(Rating::getRating)
                                .mapToDouble(BigDecimal::doubleValue)
                                .average()
                                .orElse(0.0);
                    })
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);
        }

        List<String> overallOpinions = reviews.stream()
                .map(Review::getOverallOpinion)
                .filter(StringUtils::hasText)
                .toList();

        return ReviewSummaryResponse.builder()
                .reviewCount(reviewCount)
                .overallAverageRating(overallAverageRating)
                .overallOpinions(overallOpinions)
                .build();
    }

    @Transactional(readOnly = true)
    public ReviewSummaryResponse getTodayReviews(LocalDate date, MealType mealType) {
        String dayInfo = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Meal meal = mealRepository.findByDayInfoStartingWithAndMealType(dayInfo, mealType);

        List<MealMenu> mealMenus = meal.getMealMenus();
        List<Review> mealReviews = reviewRepository.findByMeal(meal);

        List<MenuReviewSummaryResponse> menuSummaries = mealMenus.stream().map(mealMenu -> {
            Menu menu = mealMenu.getMenu();

            Question question = questionRepository.findByMenu(menu);
            String aiQuestion = question != null ? question.getContent() : null;

            List<Rating> ratingsForMenu = mealReviews.stream()
                    .flatMap(review -> ratingRepository.findByReview(review).stream())
                    .filter(rating -> rating.getMenu().equals(menu))
                    .toList();

            double averageRating = 0.0;
            if (!ratingsForMenu.isEmpty()) {
                averageRating = ratingsForMenu.stream()
                        .map(Rating::getRating)
                        .mapToDouble(BigDecimal::doubleValue)
                        .average()
                        .orElse(0.0);
            }

            List<String> userResponses = mealReviews.stream()
                    .flatMap(review -> answerRepository.findByReview(review).stream())
                    .filter(answer -> answer.getQuestion().getMenu().equals(menu))
                    .map(Answer::getContent)
                    .filter(StringUtils::hasText)
                    .toList();

            return new MenuReviewSummaryResponse(
                    menu.getName(),
                    averageRating,
                    aiQuestion,
                    userResponses
            );
        }).toList();

        long reviewCount = mealReviews.size();

        double overallAverageRating = 0.0;
        if (!mealReviews.isEmpty()) {
            overallAverageRating = mealReviews.stream()
                    .flatMap(review -> ratingRepository.findByReview(review).stream())
                    .map(Rating::getRating)
                    .mapToDouble(BigDecimal::doubleValue)
                    .average()
                    .orElse(0.0);
        }

        List<String> overallOpinions = mealReviews.stream()
                .map(Review::getOverallOpinion)
                .filter(StringUtils::hasText)
                .toList();

        return ReviewSummaryResponse.builder()
                .reviewCount(reviewCount)
                .overallAverageRating(overallAverageRating)
                .overallOpinions(overallOpinions)
                .menuReviews(menuSummaries)
                .build();
    }
}
