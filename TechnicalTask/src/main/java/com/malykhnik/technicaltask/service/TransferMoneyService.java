package com.malykhnik.technicaltask.service;

public interface TransferMoneyService {
    void transfer(Long userFromId, Long UserToId, double money);
}
