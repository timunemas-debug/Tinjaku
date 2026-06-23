package com.tinjaku.model;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Getter
@Setter
public class User {
    private Long userId;
    private String namaUser, alamatLengkap;
    private Kota kota;
    
    @JsonIgnore
    private List<Pesanan> pesananList;
    public User(){
    }

    public User(Long userId, String namaUser, String alamatLengkap, Kota kota){
        this.userId = userId;
        this.namaUser = namaUser;
        this.alamatLengkap = alamatLengkap;
        this.kota = kota;
        this.pesananList = new ArrayList<>();
    }
}