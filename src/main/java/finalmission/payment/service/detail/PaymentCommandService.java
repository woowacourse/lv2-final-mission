package finalmission.payment.service.detail;

import finalmission.payment.domain.Payment;
import finalmission.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentCommandService {

    private final PaymentRepository paymentRepository;

    public Payment create(final Payment payment) {
        return paymentRepository.save(payment);
    }

    public void delete(final Payment payment) {
        paymentRepository.delete(payment);
    }
}
