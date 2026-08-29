package com.tinjaku.dto.response;
import java.math.BigDecimal;

import com.tinjaku.model.Kota;
import com.tinjaku.model.Label;
import com.tinjaku.model.StatusPesanan;
import com.tinjaku.model.UkuranSepticTank;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PesananResponse {
    private Long id;
    private Long userId;
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
    private Label label;
    private UkuranSepticTank ukuranSepticTank;
    private BigDecimal totalHarga;
}