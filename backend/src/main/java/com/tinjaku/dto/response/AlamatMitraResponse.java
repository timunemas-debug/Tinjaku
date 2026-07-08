package com.tinjaku.dto.response;

import com.tinjaku.model.LabelMitra;
import com.tinjaku.model.Kota;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlamatMitraResponse {
    private LabelMitra labelMitra;
    private String jalan;
    private String kelurahan;
    private String kecamatan;
    private Kota kota;
    private String provinsi;
}
