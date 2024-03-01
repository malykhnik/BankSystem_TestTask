package com.malykhnik.technicaltask.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferRequest {
    private Long userFromId;
    private Long userToId;
    private Double money;
}
