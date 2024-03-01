package com.malykhnik.technicaltask.repository;

import com.malykhnik.technicaltask.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    void deleteById(Long id);

    User findByEmail(String email);

    User findByPhone(String phone);

    User findByUsername(String username);

    User findUserById(Long id);

    //выбираем юзеров, чья дата рождения больше, чем переданная в запросе
    @Query("SELECT u FROM User u WHERE u.dateOfBirth > :birthdate")
    List<User> findByBirthDateGreaterThan(@Param("birthdate") Date birthdate);

    //выбираем юзера по 100% сходству phone
    @Query("SELECT u FROM User u WHERE u.phone = :phone")
    User findUserByPhone(@Param("phone") String phone);

    //выбираем юзероа по формату ‘{text-from-request-param}%’
    @Query("SELECT u FROM User u WHERE u.fullName LIKE CONCAT(:fullName, '%')")
    List<User> findUserByFullName(@Param("fullName") String fullName);

    //выбираем юзера, по 100% сходству email
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findUserByEmail(@Param("email") String email);
}
