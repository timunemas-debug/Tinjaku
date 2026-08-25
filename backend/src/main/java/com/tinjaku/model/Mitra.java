package com.tinjaku.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class Mitra {

    @Enumerated(EnumType.STRING)
    private StatusOnOff statusOnOff = StatusOnOff.OFFLINE;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_MITRA;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mitraId;

    private String namaMitra;
    private BigDecimal hargaJasa;
    private Double latitude;
    private Double longitude;
    private LocalDateTime updateAt;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @OneToMany(mappedBy = "mitra")
    private List<AlamatMitra> alamatList = new ArrayList<>();

    @OneToMany(mappedBy = "mitra")
    private List<Pesanan> pesananList = new ArrayList<>();

    @OneToMany(mappedBy = "mitra")
    private List<Notification> notificationList = new ArrayList<>();

    @OneToMany(mappedBy = "mitra")
    private List<OfferPesanan> offerPesanan = new ArrayList<>();
    
    public Mitra(){
    }

    public Mitra(Long mitraId, String namaMitra){
        this.mitraId = mitraId;
        this.namaMitra = namaMitra;
    }
}