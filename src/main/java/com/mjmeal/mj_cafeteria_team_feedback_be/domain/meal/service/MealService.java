package com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.service;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.dto.MealResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.entity.Meal;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.repository.MealRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.MealType;
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
}
