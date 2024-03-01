package com.malykhnik.technicaltask.service.impls;

import com.malykhnik.technicaltask.model.BankAccount;
import com.malykhnik.technicaltask.model.User;
import com.malykhnik.technicaltask.repository.BankAccountRepository;
import com.malykhnik.technicaltask.repository.UserRepository;
import com.malykhnik.technicaltask.service.BalanceService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
            double currentBalance = user.getBankAccount().getBalance();
            if (currentBalance >= user.getTopBalance() * 2.07) {
                continue;
            }
            double newBalance = currentBalance * 1.05;
            if (newBalance < user.getTopBalance() * 2.07) {
                countOfIncreasedBalances += 1;
            }
            if (newBalance > user.getTopBalance() * 2.07) {
                newBalance = user.getTopBalance() * 2.07;
            }
            user.getBankAccount().setBalance(newBalance);
            BankAccount bankAccount = user.getBankAccount();
            bankAccountRepository.save(bankAccount);
        }
        logger.info("increased balances of {} users", countOfIncreasedBalances);
    }
}
