package com.malykhnik.technicaltask.controller;

import com.malykhnik.technicaltask.entity.User;
import com.malykhnik.technicaltask.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Tag(name = "user_security_controller")
@RestController
@RequestMapping("/security_api/users")
@AllArgsConstructor
public class UserSecurityController {
    private static final Logger logger = LoggerFactory.getLogger(UserSecurityController.class);

    private final UserService userService;

    @Operation(
            summary = "получает пользователей с датеой рождения больше, чем в запросе"
    )
    @GetMapping("/searchByBirthday")
    public List<User> getUsersByBirthdayFilter(@RequestParam("birthDate")
                                               @DateTimeFormat(pattern = "yyyy-MM-dd") Date dayOfBirthday) {
        logger.info("Getting user by birthday");
        return userService.getUsersByBirthdayGreaterThan(dayOfBirthday);
    }

    @Operation(
            summary = "получает пользователя с конкретным телефоном"
    )
    @GetMapping("/searchByPhone")
    public User getUserByPhone(@RequestParam("phone") String phone) {
        logger.info("Getting user by phone");
        return userService.getUserByPhone(phone);
    }

    @Operation(
            summary = "получает пользователя по ФИО"
    )
    @GetMapping("/searchByFullName")
    public List<User> getUserByFullName(@RequestParam("fullName") String fullName) {
        logger.info("Getting user by fullname");
        return userService.getUserByFullName(fullName);
    }

    @Operation(
            summary = "получает пользователя с конкретным емейлом"
    )
    @GetMapping("/searchByEmail")
    public User getUserByEmail(@RequestParam("email") String email) {
        logger.info("Getting user by email");
        return userService.getUserByEmail(email);
    }

    @Operation(
            summary = "меняет телефон у конкретного пользователя",
            description = "если такого номера телефона еще нет ни у кого в БД, то обновить" +
                    "номер телефона пользователя"
    )
    @PutMapping("/updatePhone/{id}")
    public void updatePhoneOfTheUser(@PathVariable Long id,
                                     @RequestParam(value = "phone", required = false) String phone) {
        User user = userService.findUserById(id);
        logger.info("Update user phone - {} for user with username: {}", user.getPhone(), user.getUsername());

        //если такого телефона ни у кого нет, то обновить у пользователя номер телефона
        if (userService.findByPhone(phone) == null) {
            user.setPhone(phone);
            logger.info("User get new phone - {}", phone);
            userService.saveUser(user);
        }
    }

    @Operation(
            summary = "меняет телефон у конкретного пользователя",
            description = "если такого номера телефона еще нет ни у кого в БД, то обновить" +
                    "номер телефона пользователя"
    )
    @PutMapping("/updateEmail/{id}")
    public void updateEmailOfTheUser(@PathVariable Long id,
                                     @RequestParam(value = "email", required = false) String email) {
        User user = userService.findUserById(id);
        logger.info("Update user email - {} for user with username: {}", user.getEmail(), user.getUsername());

        //если такого email ни у кого нет, то обновить у пользователя email
        if (userService.findByEmail(email) == null) {
            user.setEmail(email);
            logger.info("User get new email - {}", email);
            userService.saveUser(user);
        }
    }

    @Operation(
            summary = "удаляет номер телефона или почту у конкретного пользователя",
            description = "удаляет номер телефона или почту в зависимости от параметра type," +
                    "но при этом нельзя удалить телефон, если почты нет и наоборот"
    )
    @DeleteMapping("/delete/{id}")
    public void deleteEmailOrPhone(@PathVariable Long id,
                                   @RequestParam(value = "type", required = false) String type) {
        User user = userService.findUserById(id);
        logger.info("Delete user {} for user with username: {}", type, user.getUsername());

        if (user != null) {
            if (type.equals("email")) {
                if (user.getEmail() == null) {
                    logger.error("Your email is empty");
                    throw new RuntimeException("Your email is empty");
                } else if (user.getPhone() == null) {
                    logger.error("Phone is null - you can not delete email");
                    throw new RuntimeException("Phone is null - you can not delete email");
                } else {
                    user.setEmail(null);
                    logger.info("User with username - {} set email - null", user.getUsername());
                    userService.saveUser(user);
                }
            } else if (type.equals("phone")) {
                if (user.getPhone() == null) {
                    logger.error("Your phone is empty");
                    throw new RuntimeException("Your phone is empty");
                } else if (user.getEmail() == null) {
                    logger.error("Email is null - you can not delete phone");
                    throw new RuntimeException("Email is null - you can not delete phone");
                } else {
                    user.setPhone(null);
                    logger.info("User with username - {} set phone - null", user.getUsername());
                    userService.saveUser(user);
                }
            } else {
                logger.error("Wrong type!!!");
                throw new RuntimeException("Wrong type!!!");
            }
        } else {
            logger.error("User does not exist");
            throw new RuntimeException("User does not exist");
        }
    }

}
