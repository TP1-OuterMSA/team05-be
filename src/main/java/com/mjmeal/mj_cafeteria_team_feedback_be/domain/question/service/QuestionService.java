package com.mjmeal.mj_cafeteria_team_feedback_be.domain.question.service;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.entity.Meal;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.entity.MealMenu;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.repository.MealRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.entity.Menu;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.question.dto.QuestionItemDto;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.question.dto.QuestionResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.question.entity.Question;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.question.repository.QuestionRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.MealType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final MealRepository mealRepository;
    private final QuestionRepository questionRepository;

    public QuestionResponse getQuestionList(MealType mealType) {
        String todayStr = LocalDate.now(ZoneId.of("Asia/Seoul"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Meal meal = mealRepository.findByDayInfoStartingWithAndMealType(todayStr, mealType);

        List<Menu> menus = meal.getMealMenus().stream()
                .map(MealMenu::getMenu)
                .toList();

        List<Long> menuIds = menus.stream()
                .map(Menu::getId)
                .toList();

        List<Question> questions = questionRepository.findByMenuIdIn(menuIds);

        List<QuestionItemDto> questionItems = questions.stream()
                .map(q -> new QuestionItemDto(q.getMenu().getName(), q.getContent()))
                .toList();

        return new QuestionResponse(questionItems);
    }
}
