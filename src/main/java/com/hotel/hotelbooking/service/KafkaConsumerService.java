package com.hotel.hotelbooking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.hotelbooking.entity.StatisticEvent;
import com.hotel.hotelbooking.repository.StatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaConsumerService {

    private final StatisticRepository repository;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topics.user-events}")
    private String userEventsTopic;

    @Value("${kafka.topics.booking-events}")
    private String bookingEventsTopic;

    @KafkaListener(topics = "#{__listener.userEventsTopic}", groupId = "${kafka.group-id}")
    public void consumeUserEvent(String message) {
        StatisticEvent event = new StatisticEvent();
        event.setEventType("USER_REGISTRATION");
        event.setData(message);
        repository.save(event);
    }

    @KafkaListener(topics = "#{__listener.bookingEventsTopic}", groupId = "${kafka.group-id}")
    public void consumeBookingEvent(String message) {
        StatisticEvent event = new StatisticEvent();
        event.setEventType("ROOM_BOOKING");
        event.setData(message);
        repository.save(event);
    }
}
