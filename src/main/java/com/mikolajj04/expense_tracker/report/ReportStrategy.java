package com.mikolajj04.expense_tracker.report;
import com.mikolajj04.expense_tracker.dto.ReportResponse;
import java.util.List;

public interface ReportStrategy {

    String getReportType();


    List<ReportResponse> generateReport(Long userId, Integer month, Integer year);
}