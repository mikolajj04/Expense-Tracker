package com.mikolajj04.expense_tracker.service;
import com.mikolajj04.expense_tracker.dto.ReportResponse;
import com.mikolajj04.expense_tracker.report.ReportStrategy;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final Map<String, ReportStrategy> strategies;

    public ReportService(List<ReportStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(ReportStrategy::getReportType, Function.identity()));
    }

    public List<ReportResponse> getReport(String type, Long userId, Integer month, Integer year) {
        ReportStrategy strategy = strategies.get(type.toUpperCase());

        if (strategy == null) {
            throw new RuntimeException("Report type not supported: " + type);
        }

        return strategy.generateReport(userId, month, year);
    }
}