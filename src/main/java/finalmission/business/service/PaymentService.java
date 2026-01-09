package finalmission.business.service;

import finalmission.business.model.entity.Payment;
import finalmission.business.model.entity.Reservation;
import finalmission.infrastructure.repository.PaymentRepository;
import finalmission.presentation.TossPaymentClient;
import finalmission.presentation.dto.PaymentApproveResponseDto;
import finalmission.presentation.dto.PaymentInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentService {
    private final TossPaymentClient paymentClient;
    private final PaymentRepository paymentRepository;

    public PaymentService(TossPaymentClient paymentClient, PaymentRepository paymentRepository) {
        this.paymentClient = paymentClient;
        this.paymentRepository = paymentRepository;
    }

    public Payment pay(Reservation reservation, PaymentInfo paymentInfo) {
        PaymentApproveResponseDto paymentApproveResponseDto = paymentClient.approvePayment(paymentInfo);
        return paymentRepository.save(Payment.create(reservation, paymentApproveResponseDto));
    }
}
