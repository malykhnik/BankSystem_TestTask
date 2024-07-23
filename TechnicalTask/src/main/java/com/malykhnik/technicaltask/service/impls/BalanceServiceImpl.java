package com.malykhnik.technicaltask.service.impls;

import com.malykhnik.technicaltask.entity.BankAccount;
import com.malykhnik.technicaltask.entity.User;
import com.malykhnik.technicaltask.repository.BankAccountRepository;
import com.malykhnik.technicaltask.repository.UserRepository;
import com.malykhnik.technicaltask.service.BalanceService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private static final Logger logger = LoggerFactory.getLogger(BalanceServiceImpl.class);

    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    @Override
    @Scheduled(fixedRate = 60000)
    public void increaseBalance() {
        List<User> users = userRepository.findAll();
        int countOfIncreasedBalances = 0;
        for (User user : users) {
            BigDecimal currentBalance = user.getBankAccount().getBalance();
            if (currentBalance.compareTo(user.getTopBalance().multiply(BigDecimal.valueOf(2.07))) >= 0) {
                continue;
            }
            BigDecimal newBalance = currentBalance.multiply(BigDecimal.valueOf(1.05));
            if (newBalance.compareTo(user.getTopBalance().multiply(new BigDecimal("2.07"))) < 0) {
                countOfIncreasedBalances += 1;
            }
            if (newBalance.compareTo(user.getTopBalance().multiply(new BigDecimal("2.07"))) > 0) {
                newBalance = user.getTopBalance().multiply(new BigDecimal("2.07"));
            }
            user.getBankAccount().setBalance(newBalance);
            BankAccount bankAccount = user.getBankAccount();
            bankAccountRepository.save(bankAccount);
        }
        logger.info("increased balances of {} users", countOfIncreasedBalances);
    }
}
