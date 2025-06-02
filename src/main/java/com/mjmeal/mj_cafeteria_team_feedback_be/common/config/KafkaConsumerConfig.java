package com.mjmeal.mj_cafeteria_team_feedback_be.common.config;

import com.example.kafka_schemas.MealEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MealEvent> kafkaListenerContainerMealFactory(
            ConsumerFactory<String, MealEvent> mealConsumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, MealEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(mealConsumerFactory);

        return factory;
    }
}
