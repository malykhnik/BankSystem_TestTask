package com.malykhnik.technicaltask.controller;

import com.malykhnik.technicaltask.model.BankAccount;
import com.malykhnik.technicaltask.model.User;
import com.malykhnik.technicaltask.service.BalanceService;
import com.malykhnik.technicaltask.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "user_controller")
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final BalanceService balanceService;

    @Operation(
            summary="создает нового пользователя в БД",
            description = "получает объект User, проверяет, чтобы с такими email, phone, username не было юзеров в БД" +
                    "и проверяет, что баланс не отрицательный. Далее проверяет, что не были переданы пустые телефон и емейл" +
                    "потом присваивает все поля к объектам и отсылает объект в севрис"
    )
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
            logger.error("email and phone should not be null together!");
            throw new RuntimeException("email and phone should be not null");
        }

        user.setEmail(user.getEmail());
        user.setPhone(user.getPhone());
        user.setUsername(user.getUsername());

        BankAccount bankAccount = new BankAccount();
        user.setBankAccount(bankAccount);
        bankAccount.setBalance(user.getTopBalance());

        logger.info("info about new user: {}", user);

        return userService.saveUser(user);
    }


}
