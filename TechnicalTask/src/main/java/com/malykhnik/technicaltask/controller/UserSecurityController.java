package com.malykhnik.technicaltask.controller;

import com.malykhnik.technicaltask.model.User;
import com.malykhnik.technicaltask.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/security_api/users")
@AllArgsConstructor
public class UserSecurityController {
    private final UserService userService;

    @GetMapping("/searchByBirthday")
    public List<User> getUsersByBirthdayFilter(@RequestParam("birthDate")
                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date dayOfBirthday) {
        return userService.getUsersByBirthdayGreaterThan(dayOfBirthday);
    }

    @GetMapping("/searchByPhone")
    public User getUserByPhone(@RequestParam("phone") String phone) {
        return userService.getUserByPhone(phone);
    }

    @GetMapping("/searchByFullName")
    public List<User> getUserByFullName(@RequestParam("fullName") String fullName) {
        return userService.getUserByFullName(fullName);
    }

    @GetMapping("/searchByEmail")
    public User getUserByEmail(@RequestParam("email") String email) {
        return userService.getUserByEmail(email);
    }
}
