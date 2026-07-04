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
    private String namaPenerima;
    private String alamatLengkap;
    private String kelurahan;
    private String kecamatan;
    private Kota kota;
    private String provinsi;
    private String namaLengkap;
    private String namaMitra;
}