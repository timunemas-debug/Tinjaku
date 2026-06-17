package com.tinjaku.model;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter
@Setter
public class User {
    private Long userId;
    private String nama;
    private List<Pesanan> pesananList;
    public User(){
    }

    public User(Long userId, String nama){
        this.userId = userId;
        this.nama = nama;
        this.pesananList = new ArrayList<>();
    }
}
