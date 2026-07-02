package com.tinjaku.dto.response;

import com.tinjaku.model.Kota;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String nama;
    private List<AlamatResponse> alamat;
    private Kota kota;
}
