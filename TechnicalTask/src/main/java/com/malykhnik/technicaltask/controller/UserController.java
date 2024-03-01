package com.malykhnik.technicaltask.controller;

import com.malykhnik.technicaltask.model.BankAccount;
import com.malykhnik.technicaltask.model.User;
import com.malykhnik.technicaltask.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()) != null ||
                userService.findByPhone(user.getPhone()) != null ||
                userService.findByUsername(user.getUsername()) != null ||
                user.getTopBalance() < 0) {
            throw new RuntimeException("Wrong parametrs");
        }
        //не даем создать юзера без телефона и емейла
        if (user.getEmail() == null && user.getPhone() == null) {
            throw new RuntimeException("email and phone should be not null");
        }

        user.setEmail(user.getEmail());
        user.setPhone(user.getPhone());
        user.setUsername(user.getUsername());

        BankAccount bankAccount = new BankAccount();
        user.setBankAccount(bankAccount);
        bankAccount.setBalance(user.getTopBalance());
        return userService.saveUser(user);
    }


}
