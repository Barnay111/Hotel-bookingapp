package com.hotel.hotelbooking.controller;

import com.hotel.hotelbooking.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/statistics/export")
    public String exportStatistics() throws IOException {
        String filePath = "statistics.csv";
        statisticService.exportToCsv(filePath);
        return "CSV file created: " + filePath;
    }
}
