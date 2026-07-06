package com.tinjaku.dto.response;

import com.tinjaku.model.Kota;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MitraResponse {
    private String nama;
    private Kota kota;
    private Double ratingMitra;
    private Long totalRating;
}