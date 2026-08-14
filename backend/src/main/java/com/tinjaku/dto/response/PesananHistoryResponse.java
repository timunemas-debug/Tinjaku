package com.tinjaku.dto.response;

import java.time.LocalDateTime;

import com.tinjaku.model.StatusPesanan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PesananHistoryResponse {
    
    private StatusPesanan status;
    private LocalDateTime waktuPerubahan;
}