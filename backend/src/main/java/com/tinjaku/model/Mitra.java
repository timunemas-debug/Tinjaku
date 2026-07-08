package com.tinjaku.model;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class Mitra {

    @Enumerated(EnumType.STRING)
    private StatusOnOff statusOnOff = StatusOnOff.OFFLINE;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mitraId;

    private String namaMitra;

    @OneToMany(mappedBy = "mitra")
    private List<AlamatMitra> alamatList = new ArrayList<>();

    @OneToMany(mappedBy = "mitra")
    private List<Pesanan> pesananList = new ArrayList<>();
    
    public Mitra(){
    }

    public Mitra(Long mitraId, String namaMitra){
        this.mitraId = mitraId;
        this.namaMitra = namaMitra;
    }
}