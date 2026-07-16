package com.tinjaku.dto.response;
import java.util.List;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MitraResponse {
    private String nama;
    private Double ratingMitra;
    private Long totalRating;
    private List<AlamatMitraResponse> alamat;
}