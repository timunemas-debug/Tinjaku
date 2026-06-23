package com.tinjaku.dto;

import com.tinjaku.model.Kota;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class MitraResponse {
    private Long id;
    private String nama;
    private Kota kota;
}