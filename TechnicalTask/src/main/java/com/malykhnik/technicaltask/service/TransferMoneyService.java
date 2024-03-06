package com.malykhnik.technicaltask.service;

import java.math.BigDecimal;

public interface TransferMoneyService {
    void transfer(Long userFromId, Long UserToId, BigDecimal money);
}
