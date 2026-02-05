package com.hotel.hotelbooking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.hotelbooking.entity.StatisticEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.hotel.hotelbooking.repository.StatisticRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final StatisticRepository statisticRepository;
    private final ObjectMapper objectMapper;

    public void saveEvent(StatisticEvent event) {
        statisticRepository.save(event);
    }

    public void exportToCsv(String filePath) throws IOException {
        List<StatisticEvent> events = statisticRepository.findAll();
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("id,eventType,data,createdAt\n");
            for (StatisticEvent event : events) {
                writer.write(String.format("%d,%s,\"%s\",%s\n",
                        event.getId(),
                        event.getEventType(),
                        event.getData().replace("\"", "\"\""),
                        event.getCreatedAt()));
            }
        }
    }
}
