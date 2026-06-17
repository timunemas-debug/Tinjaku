package com.tinjaku.model;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
public class Pesanan {
    private Long id, userId;

    private String alamat, keluhan;
    private StatusPesanan status;

    public Pesanan(){
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
