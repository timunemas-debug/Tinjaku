package com.tinjaku.dto.response;

import lombok.*;
import com.tinjaku.model.Label;
import com.tinjaku.model.Kota;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlamatResponse {
    private Long idALamat;
    private Label label;
    private String jalan;
    private String kelurahan;
    private String kecamatan;
    private Kota kota;
    private String provinsi;
}
