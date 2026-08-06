package com.tinjaku.model;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    @OneToOne(mappedBy = "pesanan")
    private Payment payment;

    private String namaPenerima;
    private String noHp;
    private String alamatLengkap;
    private String kelurahan;
    private String kecamatan;

    @Enumerated(EnumType.STRING)
    private Kota kota;
    
    private String provinsi;

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
