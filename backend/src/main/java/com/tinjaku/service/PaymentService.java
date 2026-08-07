package com.tinjaku.service;

import org.springframework.stereotype.Service;

import com.tinjaku.dto.request.PaymentRequest;
import com.tinjaku.dto.response.PaymentResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.mapper.PaymentMapper;
import com.tinjaku.model.Payment;
import com.tinjaku.model.PaymentStatus;
import com.tinjaku.model.Pesanan;
import com.tinjaku.model.StatusPesanan;
import com.tinjaku.repository.PaymentRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PesananService pesananService;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper, PesananService pesananService){

        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.pesananService = pesananService;
    }

    @Transactional
    public PaymentResponse addPayment(PaymentRequest request){

        Pesanan pesanan = pesananService.getPesananEntityById(request.getPesananId());

        if (pesanan.getStatus() != StatusPesanan.MENUNGGU_PEMBAYARAN) {
            throw new BadRequestException("Pesanan belum dapat dibayar!");
        }

        if (pesanan.getPayment() != null) {
            throw new BadRequestException("Pembayaran sudah dibuat!");
        }

        Payment payment = paymentMapper.toEntity(request);

        payment.setPesanan(pesanan);
        payment.setAmount(pesanan.getTotalHarga());
        payment.setStatus(PaymentStatus.PENDING);

        // TEMPAT UNTUK PAYMENT GATEAWAYNYA YAAA!!!!!!!!!!!!!!!!!!!!!!


        return paymentMapper.toResponse(paymentRepository.save(payment));
    }
}