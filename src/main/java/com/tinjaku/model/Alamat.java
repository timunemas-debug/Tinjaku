package com.tinjaku.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Alamat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlamat;

    @Enumerated(EnumType.STRING)
    private Label label;

    private String namaPenerima;
    private String jalan;
    private String kelurahan;
    private String kecamatan;

    @Enumerated(EnumType.STRING)
    private Kota kota;

    private String provinsi;

    @ManyToOne
    private User user;
}
