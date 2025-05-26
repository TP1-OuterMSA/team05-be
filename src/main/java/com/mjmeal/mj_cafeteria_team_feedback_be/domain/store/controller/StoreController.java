package com.mjmeal.mj_cafeteria_team_feedback_be.domain.store.controller;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.response.ApiResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.store.dto.StoreDeleteRequest;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.store.dto.StoreRequest;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.store.dto.StoreResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "맛집")
@RestController
@RequestMapping("/api/team5/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @Operation(
            summary = "맛집 등록",
            description = """
        맛집을 등록합니다합니다.

        ✅ 맛집 유형 (`mealType`)은 아래 중 하나를 입력하세요:
        - `RESTAURANT`: 음식점
        - `CAFE`: 카페
        """
    )
    @PostMapping(value = "/admin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Void> save(@RequestPart("storeRequest") StoreRequest storeRequest,
                                  @RequestPart("image") MultipartFile multipartFile) {
        storeService.save(storeRequest, multipartFile);
        return ApiResponse.onSuccess(null);
    }

    @Operation(
            summary = "맛집 조회",
            description = """
        맛집을 조회합니다.
        """
    )
    @GetMapping("/admin")
    public ApiResponse<StoreResponse> getStores() {
        return ApiResponse.onSuccess(storeService.getStores());
    }

    @Operation(
            summary = "맛집 삭제",
            description = """
        맛집을 조회합니다.
        """
    )
    @DeleteMapping("/admin")
    public ApiResponse<Void> deleteStores(@RequestBody StoreDeleteRequest storeDeleteRequest) {
        storeService.deleteStores(storeDeleteRequest);
        return ApiResponse.onSuccess(null);
    }
}
