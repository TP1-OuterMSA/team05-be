package com.mjmeal.mj_cafeteria_team_feedback_be.domain.store.producer;

import com.example.kafka_schemas.StoreEvent;
import com.mjmeal.mj_cafeteria_team_feedback_be.domain.store.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreEventProducer {

    private final KafkaTemplate<String, StoreEvent> kafkaTemplate;

    public void sendStore(Store store) {
        StoreEvent event = new StoreEvent(store.getStoreType().getName(),
                store.getName(),
                store.getImage(),
                store.getDescription(),
                store.getUrl());

        kafkaTemplate.send("store.events", event);
    }
}
