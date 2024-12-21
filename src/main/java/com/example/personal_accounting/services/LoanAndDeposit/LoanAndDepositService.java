package com.example.personal_accounting.services.LoanAndDeposit;

import com.example.personal_accounting.dto.LoanAndDeposit.CreateLoanAndDepositDto;
import com.example.personal_accounting.dto.LoanAndDeposit.LoanAndDepositDto;
import com.example.personal_accounting.dto.LoanAndDeposit.UpdateLoanAndDepositDto;
import com.example.personal_accounting.utils.exceptions.LoanOrDepositNotFoundException;
import com.example.personal_accounting.utils.exceptions.UserNotFoundException;
import com.example.personal_accounting.utils.mappers.LoanAndDepositMapper;
import com.example.personal_accounting.models.LoanAndDeposit;
import com.example.personal_accounting.models.User;
import com.example.personal_accounting.repository.LoanAndDepositRepository;
import com.example.personal_accounting.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanAndDepositService {
    private final LoanAndDepositRepository loanAndDepositRepository;
    private final UserService userService;
    private final LoanAndDepositMapper loanAndDepositMapper;

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

        loanAndDepositRepository.save(loanAndDeposit);

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
    
    public LoanAndDepositDto update(Long id, UpdateLoanAndDepositDto dto) {
        LoanAndDeposit loanAndDeposit = loanAndDepositRepository.findById(id)
                .orElseThrow(() -> new LoanOrDepositNotFoundException("Loan/Deposit not found"));

        loanAndDeposit.setTitle(dto.getTitle());
        loanAndDeposit.setPrincipalAmount(dto.getPrincipalAmount());
        loanAndDeposit.setInterestRate(dto.getInterestRate());
        loanAndDeposit.setStartDate(dto.getStartDate());
        loanAndDeposit.setEndDate(dto.getEndDate());

        loanAndDepositRepository.save(loanAndDeposit);

        return loanAndDepositMapper.toDto(loanAndDeposit);
    }
    public void delete(Long id) {
        if (!loanAndDepositRepository.existsById(id)) {
            throw new LoanOrDepositNotFoundException("Loan/Deposit not found");
        }
        loanAndDepositRepository.deleteById(id);
    }
}
