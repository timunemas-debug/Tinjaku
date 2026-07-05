package com.tinjaku.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.tinjaku.model.Mitra;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingResponse {
    
    private int rating;
    private String deskripsi;
    private Mitra mitra;
}