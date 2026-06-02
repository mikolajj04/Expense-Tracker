package com.mikolajj04.expense_tracker.report;
import com.mikolajj04.expense_tracker.dto.ReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MonthlyCategoryReportStrategy implements ReportStrategy {


    private final JdbcTemplate jdbcTemplate;

    @Override
    public String getReportType() {
        return "MONTHLY_CATEGORY";
    }

    @Override
    public List<ReportResponse> generateReport(Long userId, Integer month, Integer year) {

        String sql = """
            SELECT c.name as category_name, SUM(e.amount) as total_amount
            FROM expenses e
            JOIN categories c ON e.category_id = c.id
            WHERE e.user_id = ? 
              AND EXTRACT(MONTH FROM e.date) = ? 
              AND EXTRACT(YEAR FROM e.date) = ?
            GROUP BY c.name
        """;


        return jdbcTemplate.query(sql, (rs, rowNum) -> new ReportResponse(
                rs.getString("category_name"),
                rs.getBigDecimal("total_amount")
        ), userId, month, year);
    }
}