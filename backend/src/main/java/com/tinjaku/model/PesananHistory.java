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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class PesananHistory {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pesananHistoryId;

    @Enumerated(EnumType.STRING)
    private StatusPesanan status;

    private LocalDateTime waktuPerubahan;

    @ManyToOne
    @JoinColumn(name = "pesanan_id")
    private Pesanan pesanan;

    public PesananHistory(){
    }
}