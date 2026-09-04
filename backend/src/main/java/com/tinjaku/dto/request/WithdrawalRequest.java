package com.tinjaku.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawalRequest {
    
    @NotNull(message = "Wajib mengisi nominal withdraw!")
    @Min(0)
    private BigDecimal amount;
}