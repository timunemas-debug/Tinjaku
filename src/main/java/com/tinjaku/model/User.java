package com.tinjaku.model;
import java.util.ArrayList;
import java.util.List;

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

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }

    public List<Pesanan> getPesananList() {
        return pesananList;
    }
    public void setPesananList(List<Pesanan> pesananList) {
        this.pesananList = pesananList;
    }
}
