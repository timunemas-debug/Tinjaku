package com.tinjaku.dto.response;

import com.tinjaku.model.Kota;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class MitraResponse {
    private String nama;
    private Kota kota;
}