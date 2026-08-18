package com.tinjaku.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLocationMitraRequest {
    
    @NotNull(message = "Latitude tidak boleh kosong!")
    private Double latitude;

    @NotNull(message = "Longitude tidak boleh kosong!")
    private Double longitude;
}