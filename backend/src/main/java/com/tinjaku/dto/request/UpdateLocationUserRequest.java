package com.tinjaku.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLocationUserRequest {
    
    @NotNull(message = "pickupLat tidak boleh kosong!")
    private Double pickupLat;

    @NotNull(message = "pickupLong tidak boleh kosong!")
    private Double pickupLong;
}