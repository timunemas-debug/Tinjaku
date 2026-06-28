package com.tinjaku.model;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Pesanan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keluhan;

    @Enumerated(EnumType.STRING)
    private StatusPesanan status;

    @ManyToOne
    @JoinColumn(name = "mitra_id")
    private Mitra mitra;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
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
