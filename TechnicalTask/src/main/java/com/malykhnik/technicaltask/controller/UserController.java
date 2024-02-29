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
                userService.findByUsername(user.getUsername()) != null) {
            return null;
        }
        //не даем создать юзера без телефона и емейла
        if (user.getEmail() == null && user.getPhone() == null) {
            return null;
        }

        user.setEmail(user.getEmail());
        user.setPhone(user.getPhone());
        user.setUsername(user.getUsername());

        BankAccount bankAccount = new BankAccount();
        user.setBankAccount(bankAccount);
        bankAccount.setBalance(user.getTopBalance());
        return userService.saveUser(user);
    }

//    @PutMapping("/update/{id}")
//    public void updatePhoneOfTheUser(@PathVariable Long id,
//                                     @RequestParam(value = "phone", required = false) String phone) {
//        User user = userService.findUserById(id);
//
//        //если такого телефона ни у кого нет, то обновить у пользователя номер телефона
//        if (user != null) {
//            if (userService.findByPhone(phone) == null) {
//                user.setPhone(phone);
//                userService.saveUser(user);
//            }
//        } else {
//            throw new RuntimeException("User does not exist");
//        }
//    }
//
//    @PutMapping("/update/{id}")
//    public void updateEmailOfTheUser(@PathVariable Long id,
//                                     @RequestParam(value = "email", required = false) String email) {
//        User user = userService.findUserById(id);
//
//        //если такого email ни у кого нет, то обновить у пользователя email
//        if (user != null) {
//            if (userService.findByEmail(email) == null) {
//                user.setEmail(email);
//                userService.save(user);
//            }
//        } else {
//            throw new RuntimeException("User does not exist");
//        }
//    }

    @DeleteMapping("/delete/{id}")
    public void deleteEmailOrPhone(@PathVariable Long id,
                                   @RequestParam(value = "type", required = false) String type) {
        User user = userService.findUserById(id);

        if (user != null) {
            if (type.equals("email")) {
                if (user.getEmail().isEmpty()) {
                    throw new RuntimeException("Your email is empty");
                } else if (user.getPhone().isEmpty()) {
                    throw new RuntimeException("Phone is null - you can not delete email ");
                } else {
                    user.setEmail(null);
                    userService.saveUser(user);
                }
            } else if (type.equals("phone")) {
                if (user.getPhone().isEmpty()) {
                    throw new RuntimeException("Your phone is empty");
                } else if (user.getEmail().isEmpty()) {
                    throw new RuntimeException("Email is null - you can not delete phone ");
                } else {
                    user.setPhone(null);
                    userService.saveUser(user);
                }
            } else {
                throw new RuntimeException("Wrong type!!!");
            }
        } else {
            throw new RuntimeException("User does not exist");
        }
    }

}
