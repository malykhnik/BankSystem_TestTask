package com.malykhnik.technicaltask.repository;

import com.malykhnik.technicaltask.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
