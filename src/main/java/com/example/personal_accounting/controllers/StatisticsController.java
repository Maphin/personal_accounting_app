package com.example.personal_accounting.controllers;

import com.example.personal_accounting.configs.JwtRequestFilter;
import com.example.personal_accounting.services.Statistics.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping(path ="/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("")
    public Map<String, BigDecimal> getTransactionStatistics(
            @RequestParam String yearMonth,
            @RequestParam(required = false) String startDate,
            @AuthenticationPrincipal JwtRequestFilter.CustomUserPrincipal principal
    ) {
        DateRange dateRange = parseDateRange(yearMonth, startDate);
        return statisticsService.getStatistics(principal.userId(), dateRange.startDate(), dateRange.endDate());
    }

    @GetMapping("/export")
    public ResponseEntity<String> exportStatistics(
            @RequestParam String yearMonth,
            @RequestParam(required = false) String startDate,
            @AuthenticationPrincipal JwtRequestFilter.CustomUserPrincipal principal
    ) {
        try {
            DateRange dateRange = parseDateRange(yearMonth, startDate);
            Map<String, BigDecimal> statistics = statisticsService.getStatistics(principal.userId(), dateRange.startDate(), dateRange.endDate());

            String fileName = generateFileName();
            String directoryPath = Paths.get(System.getProperty("user.dir"), "statistics").toString();
            createDirectoryIfNotExists(directoryPath);

            String filePath = Paths.get(directoryPath, fileName).toString();
            statisticsService.exportStatisticsToExcel(statistics, filePath);

            return ResponseEntity.ok("Statistics exported successfully. File saved at: " + filePath);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to export statistics: " + e.getMessage());
        }
    }

    private void createDirectoryIfNotExists(String directoryPath) {
        java.io.File directory = new java.io.File(directoryPath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new RuntimeException("Failed to create directory: " + directoryPath);
            }
        }
    }

    private DateRange parseDateRange(String yearMonth, String startDate) {
        YearMonth ym = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
        LocalDate defaultStartDate = ym.atDay(1);
        LocalDate start = startDate != null ? LocalDate.parse(startDate) : defaultStartDate;
        LocalDate end = ym.atEndOfMonth();
        return new DateRange(start, end);
    }

    private String generateFileName() {
        return "statistics_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx";
    }

    private record DateRange(LocalDate startDate, LocalDate endDate) {
    }
}
