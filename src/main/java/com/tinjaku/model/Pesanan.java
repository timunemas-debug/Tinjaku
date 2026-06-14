package com.tinjaku.model;

public class Pesanan {
    private Long id, userId;

    private String alamat, keluhan;
    private StatusPesanan status;
    public Pesanan(){
    }

    public Pesanan(String alamat, String keluhan, StatusPesanan status, Long id, Long userId){
        this.alamat = alamat;
        this.keluhan = keluhan;
        this.id = id;
        this.status = status;
        this.userId = userId;
    }

    public String getAlamat() {
        return alamat;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKeluhan() {
        return keluhan;
    }
    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public StatusPesanan getStatus() {
        return status;
    }

    public void setStatus(StatusPesanan status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString(){
        return "Pesanan{" +
               "id=" + id +
               ",pelangganId=" + userId +
               ",alamat=" + alamat +
               ",keluhan=" + keluhan +
               ".status=" + status +
               "}";
    }
}
