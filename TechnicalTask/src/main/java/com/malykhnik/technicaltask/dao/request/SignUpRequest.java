package com.malykhnik.technicaltask.dao.request;

import com.malykhnik.technicaltask.model.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String username;
    private String password;
    private String phone;
    private String email;
    private Date dateOfBirth;
    private String fullName;
    private double topBalance;
    private BankAccount bankAccount;
}