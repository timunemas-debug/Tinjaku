package com.tinjaku.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tinjaku.dto.response.WalletTransactionResponse;
import com.tinjaku.mapper.WalletTransactionMapper;
import com.tinjaku.model.Mitra;
import com.tinjaku.model.Pesanan;
import com.tinjaku.model.Wallet;
import com.tinjaku.model.WalletReferenceType;
import com.tinjaku.model.WalletTransaction;
import com.tinjaku.model.WalletTransactionType;
import com.tinjaku.repository.WalletRepository;
import com.tinjaku.repository.WalletTransactionRepository;
import com.tinjaku.security.SecurityService;

@ExtendWith(MockitoExtension.class)
public class WalletTransactionTest {
    
    @Mock
    WalletTransactionRepository walletTransactionRepository;

    @Mock
    WalletTransactionMapper walletTransactionMapper;

    @Mock
    WalletRepository walletRepository;

    @Mock
    SecurityService securityService;

    @InjectMocks
    WalletTransactionService walletTransactionService;

    @Test
    public void shouldAddCreditSuccessfully(){

        Mitra mitra = new Mitra();
        mitra.setMitraId(1L);

        Wallet wallet = new Wallet();
        wallet.setWalletId(1L);
        wallet.setBalance(BigDecimal.valueOf(90000));
        wallet.setCreatedAt(LocalDateTime.now());
        wallet.setMitra(mitra);

        Pesanan pesanan = new Pesanan();
        pesanan.setId(2L);
        pesanan.setHargaJasa(BigDecimal.valueOf(100000));

        WalletTransactionResponse walletTransactionResponse = new WalletTransactionResponse();

        when(securityService.getCurrentMitraId())
                .thenReturn(1L);

        when(walletRepository.findByMitraMitraId(1L))
                .thenReturn(Optional.of(wallet));

        when(walletRepository.findByWalletIdWithLock(1L))
                .thenReturn(Optional.of(wallet));

        when(walletTransactionRepository.existsByReferenceTypeAndReferenceIdAndType(WalletReferenceType.PESANAN, 2L, WalletTransactionType.CREDIT))
                .thenReturn(false);

        when(walletTransactionRepository.saveAndFlush(any(WalletTransaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(walletTransactionMapper.toMapResponse(any(WalletTransaction.class)))
                .thenReturn(walletTransactionResponse);

        WalletTransactionResponse result = walletTransactionService.addCredit(pesanan);

        assertNotNull(result);
        assertEquals(new BigDecimal(190000), wallet.getBalance());

        verify(walletTransactionRepository).saveAndFlush(any(WalletTransaction.class));
        verify(walletTransactionMapper).toMapResponse(any(WalletTransaction.class));
        
    }

    @Test
    public void shouldRejectDebitWhenBalanceInsufficient() {

        Wallet wallet = new Wallet();
        wallet.setWalletId(1L);
        wallet.setBalance(BigDecimal.valueOf(50000));

        BigDecimal amountDebit =
                BigDecimal.valueOf(75000);

        when(walletRepository.findByWalletIdWithLock(1L))
                .thenReturn(Optional.of(wallet));

        assertThrows(
                Exception.class,
                () -> walletTransactionService.addDebit(
                        1L,
                        10L,
                        amountDebit
                )
        );

        assertEquals(
                new BigDecimal("50000"),
                wallet.getBalance()
        );

        verify(walletTransactionRepository, never())
                .saveAndFlush(any(WalletTransaction.class));

        verify(walletTransactionMapper, never())
                .toMapResponse(any(WalletTransaction.class));
    }

    @Test
    public void shouldRejectDebitWhenAmountZero() {

        Wallet wallet = new Wallet();
        wallet.setWalletId(1L);
        wallet.setBalance(BigDecimal.valueOf(100000));

        when(walletRepository.findByWalletIdWithLock(1L))
                .thenReturn(Optional.of(wallet));

        assertThrows(
                Exception.class,
                () -> walletTransactionService.addDebit(
                        1L,
                        10L,
                        BigDecimal.ZERO
                )
        );

        assertEquals(
                new BigDecimal("100000"),
                wallet.getBalance()
        );

        verify(walletTransactionRepository, never())
                .saveAndFlush(any(WalletTransaction.class));
    }

    @Test
    public void shouldRejectDebitWhenAmountNegative() {

        Wallet wallet = new Wallet();
        wallet.setWalletId(1L);
        wallet.setBalance(BigDecimal.valueOf(100000));

        when(walletRepository.findByWalletIdWithLock(1L))
                .thenReturn(Optional.of(wallet));

        assertThrows(
                Exception.class,
                () -> walletTransactionService.addDebit(
                        1L,
                        10L,
                        BigDecimal.valueOf(-50000)
                )
        );

        assertEquals(
                new BigDecimal("100000"),
                wallet.getBalance()
        );

        verify(walletTransactionRepository, never())
                .saveAndFlush(any(WalletTransaction.class));
    }

    @Test
    public void shouldRejectDebitWhenAmountNull() {

        Wallet wallet = new Wallet();
        wallet.setWalletId(1L);
        wallet.setBalance(BigDecimal.valueOf(100000));

        when(walletRepository.findByWalletIdWithLock(1L))
                .thenReturn(Optional.of(wallet));

        assertThrows(
                Exception.class,
                () -> walletTransactionService.addDebit(
                        1L,
                        10L,
                        null
                )
        );

        assertEquals(
                new BigDecimal("100000"),
                wallet.getBalance()
        );

        verify(walletTransactionRepository, never())
                .saveAndFlush(any(WalletTransaction.class));
    }

    @Test
    public void shouldRejectCreditWhenWalletNotFound() {

        when(securityService.getCurrentMitraId())
                .thenReturn(1L);

        when(walletRepository.findByMitraMitraId(1L))
                .thenReturn(Optional.empty());

        Pesanan pesanan = new Pesanan();
        pesanan.setId(2L);
        pesanan.setHargaJasa(BigDecimal.valueOf(100000));

        assertThrows(
                Exception.class,
                () -> walletTransactionService.addCredit(pesanan)
        );

        verify(walletTransactionRepository, never())
                .saveAndFlush(any(WalletTransaction.class));
    }

    @Test
    public void shouldRejectDebitWhenWalletNotFound() {

        when(walletRepository.findByWalletIdWithLock(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                Exception.class,
                () -> walletTransactionService.addDebit(
                        1L,
                        10L,
                        BigDecimal.valueOf(50000)
                )
        );

        verify(walletTransactionRepository, never())
                .saveAndFlush(any(WalletTransaction.class));
    }
}