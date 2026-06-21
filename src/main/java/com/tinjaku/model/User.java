package com.tinjaku.model;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter
@Setter
public class User {
    private Long userId;
    private String namaUser, alamatLengkap, kota;
    private List<Pesanan> pesananList;
    public User(){
    }

    public User(Long userId, String namaUser, String alamatLengkap, String kota){
        this.userId = userId;
        this.namaUser = namaUser;
        this.alamatLengkap = alamatLengkap;
        this.kota = kota;
        this.pesananList = new ArrayList<>();
    }
}