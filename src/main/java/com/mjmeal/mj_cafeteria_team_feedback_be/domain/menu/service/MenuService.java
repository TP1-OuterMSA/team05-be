package com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.service;

import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.dto.MenuRequest;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.dto.MenuResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.entity.Menu;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.entity.MenuPreference;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.entity.MenuPreferenceComment;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.entity.WantMenu;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.repository.MenuPreferenceCommentRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.repository.MenuPreferenceRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.repository.MenuRepository;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.repository.WantMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuPreferenceCommentRepository menuPreferenceCommentRepository;
    private final MenuPreferenceRepository menuPreferenceRepository;
    private final WantMenuRepository wantMenuRepository;

    @Transactional
    public void saveLikeMenu(MenuRequest menuRequest) {
        for (String name : menuRequest.getMenuNames()) {
            Menu menu = menuRepository.findByName(name);

            menuPreferenceRepository.save(
                    MenuPreference.builder()
                            .menu(menu)
                            .count(0)
                            .build()
            );

            if (menuRequest.getComment() != null && !menuRequest.getComment().isBlank()) {
                menuPreferenceCommentRepository.save(
                        MenuPreferenceComment.builder()
                                .menu(menu)
                                .comment(menuRequest.getComment())
                                .build()
                );
            }
        }
    }

    @Transactional(readOnly = true)
    public MenuResponse getLikeMenu() {
        List<Menu> menuList = menuRepository.findTop8ByOrderByIdDesc();
        return new MenuResponse(menuList);
    }

    @Transactional
    public void saveFoodList(MenuRequest menuRequest) {
        wantMenuRepository.deleteAll();

        List<WantMenu> wantMenus = menuRequest.getMenuNames().stream()
                .map(name -> WantMenu.builder()
                .name(name)
                .build()).toList();
        wantMenuRepository.saveAll(wantMenus);
    }
}