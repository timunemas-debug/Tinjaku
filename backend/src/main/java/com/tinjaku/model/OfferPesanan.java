package com.tinjaku.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OfferPesanan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pesanan_id")
    private Pesanan pesanan;

    @ManyToOne
    @JoinColumn(name = "mitra_id")
    private Mitra mitra;

    @Enumerated(EnumType.STRING)
    private StatusOfferPesanan statusOfferPesanan;

    private LocalDateTime offeredAt;
    private LocalDateTime expiresAt;
    private LocalDateTime respondedAt;

}
