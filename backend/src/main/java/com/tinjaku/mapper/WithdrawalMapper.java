package com.tinjaku.mapper;

import org.springframework.stereotype.Component;

import com.tinjaku.dto.request.WithdrawalRequest;
import com.tinjaku.dto.response.WithdrawalResponse;
import com.tinjaku.model.Withdrawal;

@Component
public class WithdrawalMapper {
    
    public Withdrawal toEntity(WithdrawalRequest request){

        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(request.getAmount());

        return  withdrawal;
    }

    public WithdrawalResponse toMapResponse(Withdrawal withdrawal){
        return  new WithdrawalResponse(withdrawal.getAmount(),
                                       "Anda berhasil menarik sebesar: " + withdrawal.getAmount());
    }
}