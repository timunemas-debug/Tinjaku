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

    private String namaUser;

    @OneToMany(mappedBy = "user")
    private List<Alamat> alamatList;

    @Enumerated(EnumType.STRING)
    private Kota kota;
    
    @OneToMany(mappedBy = "user")
    private List<Pesanan> pesananList;

    public User(){
    }

    public User(Long userId, String namaUser, String alamatLengkap, Kota kota){
        this.userId = userId;
        this.namaUser = namaUser;
        this.alamatList = new ArrayList<>();
        this.kota = kota;
        this.pesananList = new ArrayList<>();
    }
}