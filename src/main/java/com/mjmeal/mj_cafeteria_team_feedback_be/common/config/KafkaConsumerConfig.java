package com.mjmeal.mj_cafeteria_team_feedback_be.common.config;

import com.example.kafka_schemas.MealEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

@EnableKafka
@Configuration
@Slf4j
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.properties.schema.registry.url}")
    private String schemaRegistryUrl;


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MealEvent> kafkaListenerContainerMealFactory(
            ConsumerFactory<String, MealEvent> mealConsumerFactory
    ) {
        log.info("Kafka 설정 - bootstrapServers: {}", bootstrapServers);
        log.info("Kafka 설정 - groupId: {}", groupId);
        log.info("Kafka 설정 - schemaRegistryUrl: {}", schemaRegistryUrl);
        ConcurrentKafkaListenerContainerFactory<String, MealEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(mealConsumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }
}
