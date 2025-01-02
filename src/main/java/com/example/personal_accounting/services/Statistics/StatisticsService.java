package com.example.personal_accounting.services.Statistics;

import com.example.personal_accounting.models.Account;
import com.example.personal_accounting.services.Accounts.AccountService;
import com.example.personal_accounting.services.Fund.FundService;
import com.example.personal_accounting.services.Statistics.Transaction.TransactionStatisticsService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final TransactionStatisticsService transactionStatisticsService;
    private final AccountService accountService;
    private final FundService fundService;
    public Map<String, BigDecimal> getStatistics(Long userId, LocalDate start, LocalDate end) {
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpenses = BigDecimal.ZERO;

        List<Account> userAccounts = accountService.getUserAccounts(userId);

        for (Account account : userAccounts) {
            totalIncome = totalIncome.add(transactionStatisticsService.getTotalIncome(start, end, account.getId()));
            totalExpenses = totalExpenses.add(transactionStatisticsService.getTotalExpenses(start, end, account.getId()));
        }

        BigDecimal totalDeposits = fundService.getTotalDeposits(userId, start, end);

        return Map.of(
                "totalIncome", totalIncome,
                "totalExpenses", totalExpenses,
                "totalFundsDeposits", totalDeposits
        );
    }

    public void exportStatisticsToExcel(Map<String, BigDecimal> statistics, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Statistics");

        int rowIndex = 0;

        Row headerRow = sheet.createRow(rowIndex++);
        headerRow.createCell(0).setCellValue("Statistic");
        headerRow.createCell(1).setCellValue("Value");

        for (Map.Entry<String, BigDecimal> entry : statistics.entrySet()) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue().doubleValue());
        }

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        }

        workbook.close();
    }
}
