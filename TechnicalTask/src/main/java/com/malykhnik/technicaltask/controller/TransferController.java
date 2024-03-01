package com.malykhnik.technicaltask.controller;


import com.malykhnik.technicaltask.model.TransferRequest;
import com.malykhnik.technicaltask.service.TransferMoneyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security_api")
@AllArgsConstructor
public class TransferController {

    private final TransferMoneyService transferMoneyService;

    @PostMapping("/transfer")
    public TransferRequest transferFromTo(@RequestBody TransferRequest transferRequest) {
        transferMoneyService.transfer(transferRequest.getUserFromId(),
                transferRequest.getUserToId(), transferRequest.getMoney());
        return transferRequest;
    }
}
