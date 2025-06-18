package finalmission.payment.service.detail;

import finalmission.common.exception.InvalidInputException;
import finalmission.common.exception.NotFoundException;
import finalmission.payment.domain.Payment;
import finalmission.payment.repository.PaymentRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.repository.ReservationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentQueryService {

    private final PaymentRepository paymentRepository;

    public Payment get(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ID에 해당하는 결제 내역을 찾을 수 없습니다."));
    }

    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    public Payment getByReservationId(final Long reservationId) {
        return paymentRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new NotFoundException("해당 예약에 대한 결제 내역을 찾을 수 없습니다."));
    }
}
