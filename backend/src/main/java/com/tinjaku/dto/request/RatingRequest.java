package com.tinjaku.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequest {
    
    @NotNull(message = "Rating wajib di isi")
    @Min(1)
    @Max(5)
    private Integer rating;

    private String deskripsi;
}