package com.tinjaku.model;

import java.util.List;
import java.util.ArrayList;

import lombok.*;

@Getter
@Setter
public class Mitra {
    private Long mitraId;
    private String namaMitra,alamatLengkap,kota;
    private List<Pesanan> pesananList;

    public Mitra(){
    }

    public Mitra(Long mitraId, String namaMitra, String alamatLengkap, String kota){
        this.mitraId = mitraId;
        this.namaMitra = namaMitra;
        this.alamatLengkap = alamatLengkap;
        this.kota = kota;
        this.pesananList = new ArrayList<>();
    }
}