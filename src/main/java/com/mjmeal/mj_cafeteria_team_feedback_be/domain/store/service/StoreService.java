package com.mjmeal.mj_cafeteria_team_feedback_be.domain.store.service;

import com.mjmeal.mj_cafeteria_team_feedback_be.common.S3Uploader;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.store.StoreType;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.store.dto.StoreRequest;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.store.dto.StoreResponse;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.store.entity.Store;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public void save(StoreRequest storeRequest, MultipartFile file) {
        String imageUrl = s3Uploader.upload(file);

        Store store = storeRepository.findByName(storeRequest.getName())
                .map(existing -> {
                    existing.update(storeRequest.getStoreType(), imageUrl, storeRequest.getDescription(), storeRequest.getUrl());
                    return existing;
                })
                .orElseGet(() -> Store.builder()
                        .storeType(storeRequest.getStoreType())
                        .name(storeRequest.getName())
                        .image(imageUrl)
                        .description(storeRequest.getDescription())
                        .url(storeRequest.getUrl())
                        .build());

        storeRepository.save(store);
    }

    @Transactional(readOnly = true)
    public StoreResponse getStores() {
        List<Store> stores = storeRepository.findAll();
        return new StoreResponse(stores);
    }
}
