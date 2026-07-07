package com.tinjaku.model;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Enumerated(EnumType.STRING)
    private StatusOnOff statusOnOff = StatusOnOff.OFFLINE;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String namaDepan;
    private String namaBelakang;

    @OneToMany(mappedBy = "user")
    private List<Alamat> alamatList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Pesanan> pesananList = new ArrayList<>();

    public User(){
    }

    public User(Long userId, String namaDepan, String namaBelakang){
        this.userId = userId;
        this.namaDepan = namaDepan;
        this.namaBelakang = namaBelakang;
    }

    public String getNamaLengkap(){
        return getNamaDepan() + " " + getNamaBelakang();
    }
}