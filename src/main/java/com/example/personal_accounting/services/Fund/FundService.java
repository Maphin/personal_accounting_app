package com.example.personal_accounting.services.Fund;

import com.example.personal_accounting.dto.Fund.CreateFundDto;
import com.example.personal_accounting.dto.Fund.FundDto;
import com.example.personal_accounting.dto.Transaction.CreateTransactionDto;
import com.example.personal_accounting.models.ExpenseCategory;
import com.example.personal_accounting.models.Fund;
import com.example.personal_accounting.models.User;
import com.example.personal_accounting.repository.FundRepository;
import com.example.personal_accounting.services.ExpenseCategory.ExpenseCategoryService;
import com.example.personal_accounting.services.Fund.State.ActiveFundState;
import com.example.personal_accounting.services.Fund.State.CompletedFundState;
import com.example.personal_accounting.services.Fund.State.FundState;
import com.example.personal_accounting.services.Transactions.TransactionService;
import com.example.personal_accounting.services.UserService;
import com.example.personal_accounting.types.TransactionType;
import com.example.personal_accounting.utils.CheckPermissions;
import com.example.personal_accounting.utils.exceptions.FundNotFoundException;
import com.example.personal_accounting.utils.mappers.FundMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FundService {
    private final FundRepository fundRepository;
    private final UserService userService;
    private final ExpenseCategoryService expenseCategoryService;
    private final TransactionService transactionService;

    public FundState getState(Fund fund) {
        return switch (fund.getStatus()) {
            case ACTIVE -> new ActiveFundState();
            case COMPLETED -> new CompletedFundState();
        };
    }

    @Transactional
    public FundDto create(CreateFundDto dto, Long userId) {
        User user = userService.findById(userId);

        Fund fund = new Fund();
        fund.setTitle(dto.getTitle());
        fund.setUser(user);
        fund.setGoalAmount(dto.getGoalAmount());

        saveFund(fund);
        return FundMapper.toDto(fund);
    }
    public Fund getFundById(Long id) {
        return fundRepository.findById(id)
                .orElseThrow(() -> new FundNotFoundException("Fund not found with ID: " + id));
    }
    public List<Fund> getAll() {
        return fundRepository.findAll();
    }
    public List<Fund> getAllFundsByUserId(Long userId) {
        return fundRepository.findByUserId(userId);
    }
    @Transactional
    public FundDto addAmountToFund(Long fundId, BigDecimal amount, Long accountId, Long userId) throws AccountNotFoundException {
        Fund fund = getFundById(fundId);

        CheckPermissions.checkPermissions(fund.getUser().getId(), userId, "You do not have permissions to add money to this fund");

        FundState state = getState(fund);
        state.addAmountToFund(fund, amount, accountId, userId, this);

        return FundMapper.toDto(fund);
    }

    @Transactional
    public void closeFund(Long fundId, Long accountId, Long userId) throws AccountNotFoundException {
        Fund fund = getFundById(fundId);

        CheckPermissions.checkPermissions(fund.getUser().getId(), userId, "You do not have permissions to close this fund");

        FundState state = getState(fund);
        state.closeFund(fund, accountId, userId, this);
    }

    @Transactional
    public void deleteFund(Long id, Long userId) {
        Fund fund = getFundById(id);
        CheckPermissions.checkPermissions(fund.getUser().getId(), userId, "You do not have permissions to delete this fund");

        FundState state = getState(fund);
        state.deleteFund(fund, userId, this);
    }

    public void topUpFund(Fund fund, BigDecimal amount) {
        fund.setCurrentAmount(fund.getCurrentAmount().add(amount));
        saveFund(fund);
    }
    public void makeTransaction(Long accountId, BigDecimal amount, Long userId, Fund fund,
                                String categoryName, TransactionType transactionType, String description) throws AccountNotFoundException {
        CreateTransactionDto transactionDto = new CreateTransactionDto();
        transactionDto.setAccountId(accountId);

        ExpenseCategory category = expenseCategoryService.getCategoryByName(categoryName);
        transactionDto.setCategoryId(category.getId());

        transactionDto.setAmount(amount);
        transactionDto.setTransactionType(transactionType.name());
        transactionDto.setDescription(description + fund.getTitle());
        transactionDto.setTransactionDate(LocalDate.now());
        transactionDto.setPeriodic(false);
        transactionDto.setRepeatInterval(null);

        transactionService.createTransaction(transactionDto, userId);
    }
    public void saveFund(Fund fund) {
        fundRepository.save(fund);
    }
    public void deleteFundFromRepository(Fund fund) {
        fundRepository.deleteById(fund.getId());
    }
}
