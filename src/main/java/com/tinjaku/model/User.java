package com.tinjaku.model;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

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
        this.alamatList = new ArrayList<>();
        this.pesananList = new ArrayList<>();
    }

    public String getNamaLengkap(){
        return getNamaDepan() + " " + getNamaBelakang();
    }
}