package com.tinjaku.model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Pesanan {
    private Long id, userId, mitraId;

    private String keluhan;
    private StatusPesanan status;

    public Pesanan(){
    }

    @Override
    public String toString(){
        return "Pesanan{" +
               "id=" + id +
               ",pelangganId=" + userId +
               ",keluhan=" + keluhan +
               ".status=" + status +
               "}";
    }
}
