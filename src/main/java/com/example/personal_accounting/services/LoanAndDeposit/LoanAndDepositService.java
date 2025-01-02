package com.example.personal_accounting.services.LoanAndDeposit;

import com.example.personal_accounting.dto.LoanAndDeposit.CreateLoanAndDepositDto;
import com.example.personal_accounting.dto.LoanAndDeposit.LoanAndDepositDto;
import com.example.personal_accounting.dto.LoanAndDeposit.PartialUpdateLoanAndDepositDto;
import com.example.personal_accounting.models.Flyweight.LoanAndDepositFlyweight;
import com.example.personal_accounting.models.Flyweight.LoanAndDepositFlyweightFactory;
import com.example.personal_accounting.types.Type;
import com.example.personal_accounting.utils.exceptions.LoanOrDepositNotFoundException;
import com.example.personal_accounting.utils.mappers.LoanAndDepositMapper;
import com.example.personal_accounting.models.LoanAndDeposit;
import com.example.personal_accounting.models.User;
import com.example.personal_accounting.repository.LoanAndDepositRepository;
import com.example.personal_accounting.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class LoanAndDepositService {
    private final LoanAndDepositRepository loanAndDepositRepository;
    private final UserService userService;
    private final LoanAndDepositMapper loanAndDepositMapper;
    private final LoanAndDepositCalculations calculator;

    @Transactional
    public LoanAndDepositDto create(CreateLoanAndDepositDto dto, Long userId) {
        User user = userService.findById(userId);

        LoanAndDeposit loanAndDeposit = new LoanAndDeposit();
        loanAndDeposit.setUser(user);
        loanAndDeposit.setTitle(dto.getTitle());
        loanAndDeposit.setType(dto.getType());
        loanAndDeposit.setPrincipalAmount(dto.getPrincipalAmount());
        loanAndDeposit.setInterestRate(dto.getInterestRate());
        loanAndDeposit.setStartDate(dto.getStartDate());
        loanAndDeposit.setEndDate(dto.getEndDate());

        save(loanAndDeposit);

        return loanAndDepositMapper.toDto(loanAndDeposit);
    }
    public LoanAndDepositDto getById(Long id) {
        LoanAndDeposit loanAndDeposit = loanAndDepositRepository.findById(id)
                .orElseThrow(() -> new LoanOrDepositNotFoundException("Loan/Deposit not found"));
        return loanAndDepositMapper.toDto(loanAndDeposit);
    }

    public List<LoanAndDepositDto> getAllByUser(Long userId) {
        return loanAndDepositRepository.findAllByUserId(userId)
                .stream()
                .map(loanAndDepositMapper::toDto)
                .toList();
    }

    public LoanAndDepositDto update(Long id, PartialUpdateLoanAndDepositDto dto) {
        LoanAndDeposit loanAndDeposit = loanAndDepositRepository.findById(id)
                .orElseThrow(() -> new LoanOrDepositNotFoundException("Loan/Deposit not found"));

        dto.getTitle().ifPresent(loanAndDeposit::setTitle);
        dto.getPrincipalAmount().ifPresent(amount -> {
            loanAndDeposit.setPrincipalAmount(amount);
            loanAndDeposit.setSharedData(LoanAndDepositFlyweightFactory.getFlyweight(
                    loanAndDeposit.getType(), amount, loanAndDeposit.getInterestRate()));
        });
        dto.getInterestRate().ifPresent(rate -> {
            loanAndDeposit.setInterestRate(rate);
            loanAndDeposit.setSharedData(LoanAndDepositFlyweightFactory.getFlyweight(
                    loanAndDeposit.getType(), loanAndDeposit.getPrincipalAmount(), rate));
        });
        dto.getStartDate().ifPresent(loanAndDeposit::setStartDate);
        dto.getEndDate().ifPresent(loanAndDeposit::setEndDate);

        save(loanAndDeposit);

        return loanAndDepositMapper.toDto(loanAndDeposit);
    }

    public void delete(Long id) {
        if (!loanAndDepositRepository.existsById(id)) {
            throw new LoanOrDepositNotFoundException("Loan/Deposit not found");
        }
        loanAndDepositRepository.deleteById(id);
    }

    @Transactional
    public BigDecimal calculateTotalAmount(Long id) {
        LoanAndDeposit loanAndDeposit = loanAndDepositRepository.findById(id)
                .orElseThrow(() -> new LoanOrDepositNotFoundException("Loan/Deposit not found"));

        long days = DAYS.between(loanAndDeposit.getStartDate(), loanAndDeposit.getEndDate());

        LoanAndDepositFlyweight flyweight = loanAndDeposit.getSharedData();
        if (flyweight == null) {
            throw new IllegalStateException("Flyweight not initialized for LoanAndDeposit");
        }

        BigDecimal principal = flyweight.getPrincipalAmount();
        BigDecimal rate = flyweight.getInterestRate();
        Type type = flyweight.getType();

        if (type == Type.DEPOSIT) {
            return calculator.calculateDeposit(principal, rate, days);
        } else if (type == Type.LOAN) {
            return calculator.calculateLoan(principal, rate, days);
        } else {
            throw new IllegalArgumentException("Unsupported type");
        }
    }
    public void save(LoanAndDeposit loanAndDeposit) {
        loanAndDepositRepository.save(loanAndDeposit);
    }
}
