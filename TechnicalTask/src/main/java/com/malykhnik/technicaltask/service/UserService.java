package com.malykhnik.technicaltask.service;

import com.malykhnik.technicaltask.model.User;

import java.util.Date;
import java.util.List;

public interface UserService {
    User saveUser(User user);

    User findByEmail(String email);

    User findByPhone(String phone);

    User findByUsername(String username);

    User findUserById(Long id);

    List<User> getUsersByBirthdayGreaterThan(Date dayOfBirthday);

    User getUserByPhone(String phone);

    List<User> getUserByFullName(String fullName);

    User getUserByEmail(String email);
}
