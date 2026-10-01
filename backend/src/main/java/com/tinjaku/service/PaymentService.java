package com.tinjaku.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tinjaku.dto.request.PaymentRequest;
import com.tinjaku.dto.response.PaymentResponse;
import com.tinjaku.exception.BadRequestException;
import com.tinjaku.mapper.PaymentMapper;
import com.tinjaku.model.IdempotencyRecord;
import com.tinjaku.model.IdempotencyStatus;
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
    private final IdempotencyRecordService idempotencyRecordService;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper, PesananService pesananService, IdempotencyRecordService idempotencyRecordService){
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.pesananService = pesananService;
        this.idempotencyRecordService = idempotencyRecordService;
    }

    @Transactional
    public PaymentResponse addPayment(PaymentRequest request, String idempotencyKey){

        IdempotencyRecord idempotencyRecord;

        Optional<IdempotencyRecord> record = idempotencyRecordService.findByKey(idempotencyKey);

        if (record.isPresent()) {
            IdempotencyRecord existingRecord = record.get();

            if (existingRecord.getStatus() == IdempotencyStatus.PROCESSING) {
                throw new BadRequestException("Pembayaran sedang diproses!");
            }

            idempotencyRecord = existingRecord;

        }else{
            idempotencyRecord = idempotencyRecordService.createRecord(idempotencyKey);
        }

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

        Payment savedPayment = paymentRepository.save(payment);
        idempotencyRecord.setPayment(savedPayment);
        // TEMPAT UNTUK PAYMENT GATEAWAYNYA YAAA!!!!!!!!!!!!!!!!!!!!!!
        //IDEMPOTENCY KEY BELUM SELESAI MENUNGGU PAYMENT GATEAWAY

        idempotencyRecordService.save(idempotencyRecord);
        
        return paymentMapper.toResponse(savedPayment);
    }
}