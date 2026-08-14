package com.tinjaku.mapper;

import org.springframework.stereotype.Component;

import com.tinjaku.dto.response.PesananHistoryResponse;
import com.tinjaku.model.PesananHistory;

@Component
public class PesananHistoryMapper {
    public PesananHistoryResponse mapToResponse(PesananHistory pesananHistory){

        PesananHistoryResponse response = new PesananHistoryResponse();
        response.setStatus(pesananHistory.getStatus());
        response.setWaktuPerubahan(pesananHistory.getWaktuPerubahan());

        return response;
    }
}