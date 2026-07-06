package com.tinjaku.model;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class Mitra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mitraId;

    private String namaMitra,alamatLengkap;

    @Enumerated(EnumType.STRING)
    private Kota kota;

    @OneToMany(mappedBy = "mitra")
    private List<Pesanan> pesanan;
    
    public Mitra(){
    }

    public Mitra(Long mitraId, String namaMitra, String alamatLengkap, Kota kota){
        this.mitraId = mitraId;
        this.namaMitra = namaMitra;
        this.alamatLengkap = alamatLengkap;
        this.kota = kota;
        this.pesanan = new ArrayList<>();
    }
}