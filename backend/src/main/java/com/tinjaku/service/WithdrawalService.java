package com.tinjaku.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tinjaku.dto.request.WithdrawalRequest;
import com.tinjaku.dto.response.WithdrawalResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.exception.ResourceNotFound;
import com.tinjaku.mapper.WithdrawalMapper;
import com.tinjaku.model.StatusWithdrawal;
import com.tinjaku.model.Wallet;
import com.tinjaku.model.Withdrawal;
import com.tinjaku.repository.WalletRepository;
import com.tinjaku.repository.WithdrawalRepository;
import com.tinjaku.security.SecurityService;

import jakarta.transaction.Transactional;

@Service
public class WithdrawalService {
    private final WithdrawalRepository withdrawalRepository;
    private final WithdrawalMapper withdrawalMapper;
    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;
    private final SecurityService securityService;

    public WithdrawalService(WithdrawalRepository withdrawalRepository, WithdrawalMapper withdrawalMapper, WalletRepository walletRepository, WalletTransactionService walletTransactionService, SecurityService securityService){
        this.withdrawalRepository = withdrawalRepository;
        this.withdrawalMapper = withdrawalMapper;
        this.walletRepository = walletRepository;
        this.walletTransactionService = walletTransactionService;
        this.securityService = securityService;
    }

    @Transactional
    public WithdrawalResponse createWithdrawal(WithdrawalRequest request, Long walletId){

        Long mitraId = securityService.getCurrentMitraId();

        Wallet wallet = walletRepository.findByWalletIdWithLock(walletId)
                .orElseThrow(() -> new ResourceNotFound("Wallet tidak ditemukan!"));

        Withdrawal withdrawal = withdrawalMapper.toEntity(request);
        
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Jumlah saldo yang ingin anda tarik tidak valid!");
        }
        
        if (withdrawal.getAmount().compareTo(wallet.getBalance()) > 0) {
            throw new BadRequestException("Saldo anda tidak cukup untuk di withdrawal!");
        }

        if (!wallet.getMitra().getMitraId().equals(mitraId)) {
            throw new BadRequestException("Withdrawal bukan milik mitra!");
        }
        
        withdrawal.setWallet(wallet);
        withdrawal.setStatus(StatusWithdrawal.PENDING);
        withdrawal.setCreatedAt(LocalDateTime.now());

        return withdrawalMapper.toMapResponse(withdrawalRepository.save(withdrawal));
    }

    public WithdrawalResponse getWithdrawalById(Long withdrawalId){

        Withdrawal withdrawal = withdrawalRepository.findById(withdrawalId)
                .orElseThrow(() -> new ResourceNotFound("Withdrawal dengan id tersebut tidak ditemukan!"));

        return  withdrawalMapper.toMapResponse(withdrawal);
    }

    public List<WithdrawalResponse> getWithdrawalByMitra(Long mitraId){

        List<Withdrawal> withdrawalList = withdrawalRepository.findByWalletMitraMitraId(mitraId);

        return withdrawalList.stream()
                .map(withdrawalMapper::toMapResponse)
                .toList();
    }

    @Transactional
    public WithdrawalResponse processWithdrawal(Long withdrawalId){

        Withdrawal withdrawal = withdrawalRepository.findByWithdrawalIdWithLock(withdrawalId)
                .orElseThrow(() -> new ResourceNotFound("Withdrawal tidak ditemukan!"));

        if (withdrawal.getStatus() != StatusWithdrawal.PENDING) {
            throw new BadRequestException("Withdrawal sudah di proses!");
        }

        withdrawal.setStatus(StatusWithdrawal.PROCESSING);

        Long walletId = withdrawal.getWallet().getWalletId();

        walletTransactionService.addDebit(walletId, withdrawal.getWithdrawalId() , withdrawal.getAmount());

        withdrawal.setStatus(StatusWithdrawal.SUCCESS);
        withdrawal.setProcessedAt(LocalDateTime.now());

        return withdrawalMapper.toMapResponse(withdrawalRepository.save(withdrawal));
    }
}