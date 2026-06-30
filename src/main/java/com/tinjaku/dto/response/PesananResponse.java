package com.tinjaku.dto.response;
import com.tinjaku.model.Kota;
import com.tinjaku.model.StatusPesanan;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PesananResponse {
    private Long id;
    private String keluhan;
    private StatusPesanan status;
    private Kota kota;
    private String namaUser;
    private String namaMitra;
}
