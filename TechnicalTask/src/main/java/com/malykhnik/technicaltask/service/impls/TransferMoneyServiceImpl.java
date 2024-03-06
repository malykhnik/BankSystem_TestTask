package com.malykhnik.technicaltask.service.impls;

import com.malykhnik.technicaltask.model.User;
import com.malykhnik.technicaltask.repository.BankAccountRepository;
import com.malykhnik.technicaltask.repository.UserRepository;
import com.malykhnik.technicaltask.service.TransferMoneyService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class TransferMoneyServiceImpl implements TransferMoneyService {

    private static final Logger logger = LoggerFactory.getLogger(TransferMoneyServiceImpl.class);

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    @Override
    @Transactional
    public void transfer(Long userFromId, Long UserToId, BigDecimal money) {
        logger.info("transfer from {} to {} amount = {}", userRepository.findById(userFromId),
                userRepository.findById(UserToId), money);
        User userFrom = userRepository.findById(userFromId).orElseThrow(() -> new RuntimeException("User not found"));
        User userTo = userRepository.findById(UserToId).orElseThrow(() -> new RuntimeException("User not found"));

        if (userFrom.getTopBalance().compareTo(money) < 0) {
            logger.error("User does not have enough money");
            throw new RuntimeException("User does not have enough money");
        }

        BigDecimal curBalanceTo = userTo.getBankAccount().getBalance();
        userTo.getBankAccount().setBalance(curBalanceTo.add(money));
        BigDecimal curBalanceFrom = userFrom.getBankAccount().getBalance();
        userFrom.getBankAccount().setBalance(curBalanceFrom.subtract(money));

        bankAccountRepository.save(userFrom.getBankAccount());
        bankAccountRepository.save(userTo.getBankAccount());
        logger.info("successfull transfer!!!");
    }
}
