package com.malykhnik.technicaltask.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferRequest {
    @NotNull
    @Min(1)
    private Long userFromId;
    @NotNull
    @Min(1)
    private Long userToId;
    @NotNull
    @Positive
    private Double money;
}
