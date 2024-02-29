package com.malykhnik.technicaltask.service;

import com.malykhnik.technicaltask.model.User;

public interface UserService {
    User saveUser(User user);
    User findByEmail(String email);
    User findByPhone(String phone);
    User findByUsername(String username);

    User findUserById(Long id);
}
