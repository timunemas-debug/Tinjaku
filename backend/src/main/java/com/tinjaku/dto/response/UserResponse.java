package com.tinjaku.dto.response;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long userId;
    private String email;
    private String namaDepan;
    private String namaBelakang;
    private String namaLengkap;
    private List<AlamatResponse> alamat;
}