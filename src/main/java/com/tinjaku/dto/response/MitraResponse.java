package com.tinjaku.dto.response;

import com.tinjaku.model.Kota;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class MitraResponse {
    private String nama;
    private Kota kota;
}