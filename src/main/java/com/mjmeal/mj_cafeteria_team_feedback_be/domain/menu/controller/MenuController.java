package com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.controller;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.response.ApiResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.dto.MenuRequest;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.dto.MenuResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.dto.MenuSaveRequest;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.dto.WantMenuResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.menu.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "메뉴")
@RestController
@RequestMapping("/api/team5/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @Operation(
            summary = "선호 매뉴 목록 조회",
            description = """
        선호하는 매뉴 선택지를 조회합니다.
        """
    )
    @GetMapping("/like")
    public ApiResponse<MenuResponse> saveLikeMenu() {
        MenuResponse menuRequest = menuService.getLikeMenu();
        return ApiResponse.onSuccess(menuRequest);
    }

    @Operation(
            summary = "선호 매뉴 저장",
            description = """
        선호하는 매뉴를 저장합니다.

        ✅ 요청 필드:
        - `menuNames`: 메뉴명 리스트
        - `comment`: 추가의견
        """
    )
    @PostMapping("/like")
    public ApiResponse<Void> saveLikeMenu(@RequestBody MenuRequest menuRequest) {
        menuService.saveLikeMenu(menuRequest);
        return ApiResponse.onSuccess(null);
    }

    @Operation(
            summary = "관리자 선호 매뉴 목록 저장",
            description = """
        선호도 조사에 사용할 메뉴 목록을 저장합니다.

        ✅ 요청 필드:
        - `names`: 메뉴명 리스트
        """
    )
    @PutMapping("/admin/foodList")
    public ApiResponse<Void> saveFoodList(@RequestBody MenuSaveRequest menuRequest) {
        menuService.saveFoodList(menuRequest);
        return ApiResponse.onSuccess(null);
    }

    @Operation(
            summary = "관리자 선호 매뉴 목록 조회",
            description = """
        선호도 조사에 사용할 메뉴 목록을 조회합니다.
        """
    )
    @GetMapping("/admin/foolList")
    public ApiResponse<WantMenuResponse> getWantMenu() {
        return ApiResponse.onSuccess(menuService.getWantMenu());
    }
}
