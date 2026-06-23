package com.tinjaku.model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Pesanan {
    private Long id;

    private String keluhan;
    private StatusPesanan status;
    private Mitra mitra;
    private User user;
    private Kota kota;

    public Pesanan(){
    }

    @Override
    public String toString(){
        return "Pesanan{" +
               "id=" + id +
               ",keluhan=" + keluhan +
               ".status=" + status +
               "}";
    }
}
