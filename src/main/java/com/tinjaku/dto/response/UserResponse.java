package com.tinjaku.dto.response;

import com.tinjaku.model.Kota;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String nama;
    private String alamat;
    private Kota kota;
}
