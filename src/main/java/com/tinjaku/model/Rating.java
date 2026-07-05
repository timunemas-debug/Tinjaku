package com.tinjaku.model;

import jakarta.persistence.Entity;
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
@NoArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deskripsi;
    private int rating;

    @ManyToOne
    @JoinColumn(name = "mitra_id")
    private Mitra mitra;

    @OneToOne
    @JoinColumn(name = "pesanan_id")
    private Pesanan pesanan;
}