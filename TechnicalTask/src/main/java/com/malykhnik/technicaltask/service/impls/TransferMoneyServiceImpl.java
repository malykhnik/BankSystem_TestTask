package com.malykhnik.technicaltask.service.impls;

import com.malykhnik.technicaltask.model.User;
import com.malykhnik.technicaltask.repository.BankAccountRepository;
import com.malykhnik.technicaltask.repository.UserRepository;
import com.malykhnik.technicaltask.service.TransferMoneyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@AllArgsConstructor
public class TransferMoneyServiceImpl implements TransferMoneyService {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    @Override
    @Transactional
    public void transfer(Long userFromId, Long UserToId, Double money) {
        if (userFromId == null || UserToId == null || money == null) {
            throw new RuntimeException("One transfer parametr is null!!!");
        }
        User userFrom = userRepository.findById(userFromId).orElseThrow(() -> new RuntimeException("User not found"));
        User userTo = userRepository.findById(UserToId).orElseThrow(() -> new RuntimeException("User not found"));

        if (userFrom.getBankAccount().getBalance() < money) {
            throw new RuntimeException("User does not have enough money");
        }

        double curBalanceTo = userTo.getBankAccount().getBalance();
        userTo.getBankAccount().setBalance(curBalanceTo + money);
        double curBalanceFrom = userFrom.getBankAccount().getBalance();
        userFrom.getBankAccount().setBalance(curBalanceFrom - money);

        bankAccountRepository.save(userFrom.getBankAccount());
        bankAccountRepository.save(userTo.getBankAccount());
    }
}
