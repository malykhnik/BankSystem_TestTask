package com.malykhnik.technicaltask.repository;

import com.malykhnik.technicaltask.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    void deleteById(Long id);
    User findByEmail(String email);
    User findByPhone(String phone);
    User findByUsername(String username);

    User findUserById(Long id);


//    @Transactional(readOnly = true)
//    Page<User> findByDateOfBirthGreaterThan(String dateOfBirth, Pageable pageable); // Поиск пользователей с датой рождения больше переданной
//
//    @Transactional(readOnly = true)
//    Page<User> findByPhoneNumber(String phoneNumber, Pageable pageable); // Поиск пользователя по номеру телефона
//
//    @Transactional(readOnly = true)
//    Page<User> findByFullNameStartingWith(String fullName, Pageable pageable); // Поиск пользователя по началу ФИО
//
//    @Transactional(readOnly = true)
//    Page<User> findByEmail(String email, Pageable pageable); // Поиск пользователя по email
}
