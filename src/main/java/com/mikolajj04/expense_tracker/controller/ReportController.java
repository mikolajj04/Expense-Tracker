package com.mikolajj04.expense_tracker.controller;
import com.mikolajj04.expense_tracker.dto.ReportResponse;
import com.mikolajj04.expense_tracker.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/{type}")
    public ResponseEntity<List<ReportResponse>> getReport(
            @PathVariable Long userId,
            @PathVariable String type,
            @RequestParam Integer month,
            @RequestParam Integer year) {

        List<ReportResponse> report = reportService.getReport(type, userId, month, year);
        return ResponseEntity.ok(report);
    }
}