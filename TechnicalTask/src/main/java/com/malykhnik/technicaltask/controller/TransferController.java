package com.malykhnik.technicaltask.controller;


import com.malykhnik.technicaltask.model.TransferRequest;
import com.malykhnik.technicaltask.service.TransferMoneyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@Tag(name = "transfer_controller")
@RestController
@RequestMapping("/security_api")
@AllArgsConstructor
public class TransferController {

    private static final Logger logger = LoggerFactory.getLogger(TransferController.class);

    private final TransferMoneyService transferMoneyService;

    @Operation(
            summary = "переводит деньги от одного пользователя к другому",
            description = "баланс пользователя не может стать меньше 0 - проверяет в сервисе"
    )
    @PostMapping("/transfer")
    public TransferRequest transferFromTo(@Valid @RequestBody TransferRequest transferRequest) {
        logger.info("Received transfer request: {}", transferRequest);

        transferMoneyService.transfer(transferRequest.getUserFromId(), transferRequest.getUserToId(), transferRequest.getMoney());
        logger.info("Money transferred successfully");

        return transferRequest;
    }
}