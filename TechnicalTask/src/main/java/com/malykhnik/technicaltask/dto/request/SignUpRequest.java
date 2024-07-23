package com.malykhnik.technicaltask.dto.request;

import com.malykhnik.technicaltask.entity.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private BigDecimal topBalance;
    private BankAccount bankAccount;
}