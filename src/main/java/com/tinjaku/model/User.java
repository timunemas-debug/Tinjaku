package com.tinjaku.model;

public class User {
    private Long userId;
    private String nama;

    public User(){
    }

    public User(Long userId, String nama){
        this.userId = userId;
        this.nama = nama;
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
}
