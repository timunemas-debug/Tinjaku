package com.tinjaku.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "mitra_id")
    private Mitra mitra;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "pesanan")
    private Payment payment;

    @OneToMany(mappedBy = "pesanan")
    private List<PesananHistory> pesananHistory = new ArrayList<>();

    @OneToMany(mappedBy = "pesanan")
    private List<OfferPesanan> offerPesanan = new ArrayList<>();

    private String namaPenerima;
    private String noHp;
    private String alamatLengkap;
    private String kelurahan;
    private String kecamatan;
    private BigDecimal hargaJasa;
    private BigDecimal biayaTransport;
    private BigDecimal biayaAdmin;
    private BigDecimal diskon;
    private BigDecimal totalHarga;

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