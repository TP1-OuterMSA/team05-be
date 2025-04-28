package com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.consumer;


import com.example.kafka_schemas.MealEvent;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.entity.Meal;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.entity.MealMenu;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.repository.MealMenuRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.meal.repository.MealRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.entity.Menu;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.repository.MenuRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.review.MealType;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MealEventConsumer {

    private final MealRepository mealRepository;
    private final MenuRepository menuRepository;
    private final MealMenuRepository mealMenuRepository;

    @KafkaListener(topics = "meal.web.crawler.updated", groupId = "web-Crawler")
    @Transactional
    public void consume(MealEvent event) {
        MealType mealType = MealType.from(event.getMealType());
        String date = event.getDate();
        List<String> menuNames = Arrays.asList(event.getMealContents().split(" "));

        Meal meal = Meal.builder()
                .dayInfo(date)
                .mealType(mealType)
                .build();
        mealRepository.save(meal);

        for (String menuName : menuNames) {
            Menu menu = menuRepository.findByName(menuName);
            if (menu == null) {
                try {
                    menu = menuRepository.save(Menu.builder().name(menuName).build());
                } catch (DataIntegrityViolationException e) {
                    menu = menuRepository.findByName(menuName);
                    if (menu == null) {
                        throw new IllegalStateException("Menu 저장 중 충돌 발생");
                    }
                }
            }

            MealMenu mealMenu = MealMenu.builder()
                    .meal(meal)
                    .menu(menu)
                    .build();
            mealMenuRepository.save(mealMenu);
        }
    }
}