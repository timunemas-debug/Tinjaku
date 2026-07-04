package com.tinjaku.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private Long totalPesanan;
    private Long pesananMenunggu;
    private Long pesananDiTerima;
    private Long pesananDiKerjakan;
    private Long pesananSelesai;
}