package com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.service;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.dto.MealRatingResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.dto.MealResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.entity.Meal;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.entity.MealMenu;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.repository.MealRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.entity.Menu;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.entity.Rating;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.rating.repository.RatingRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.MealType;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;
    private final RatingRepository ratingRepository;

    @Transactional(readOnly = true)
    public MealResponse findMealByDateAndMealType(MealType mealType) {
        String todayStr = LocalDate.now(ZoneId.of("Asia/Seoul"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Meal meal = mealRepository.findByDayInfoStartingWithAndMealType(todayStr, mealType);

        List<String> menuNames = meal.getMealMenus().stream()
                .map(mealMenu -> mealMenu.getMenu().getName())
                .collect(Collectors.toList());

        return new MealResponse(meal.getId(), meal.getDayInfo(), meal.getMealType(), menuNames);
    }

    @Transactional(readOnly = true)
    public MealRatingResponse getMenuRating(String today) {

        List<Meal> meals = mealRepository.findByDayInfo(today);
        if (meals.isEmpty()) {
            throw new EntityNotFoundException("해당 날짜의 식단이 존재하지 않습니다.");
        }

        List<Menu> menuList = meals.stream()
            .flatMap(meal -> meal.getMealMenus().stream()
                .map(MealMenu::getMenu))
            .toList();

        List<BigDecimal> ratings = menuList.stream()
            .flatMap(menu -> ratingRepository.findAllByMenu(menu).stream())
            .map(Rating::getRating)
            .filter(Objects::nonNull)
            .toList();

        BigDecimal sum = ratings.stream()
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal average = ratings.isEmpty() ? BigDecimal.ZERO :
            sum.divide(BigDecimal.valueOf(ratings.size()), 2, RoundingMode.HALF_UP);

        return new MealRatingResponse(today, average);
    }
}
