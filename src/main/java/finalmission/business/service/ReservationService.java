package finalmission.business.service;

import finalmission.business.model.entity.Reservation;
import finalmission.exception.ReservationNotFoundException;
import finalmission.infrastructure.repository.ReservationRepository;
import finalmission.presentation.dto.ReservationRequest;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PaymentService paymentService;

    public ReservationService(ReservationRepository reservationRepository, PaymentService paymentService) {
        this.reservationRepository = reservationRepository;
        this.paymentService = paymentService;
    }

    public Reservation save(ReservationRequest request) {
        Reservation reservation = reservationRepository.save(Reservation.create(request.reservationSpec()));
        paymentService.pay(reservation, request.paymentInfo());
        return reservation;
    }

    public void save(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByMemberId(Long memberId) {
        return reservationRepository.findReservationsByMemberId(memberId);
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findReservationById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id + "번 예약을 찾을 수 없습니다"));
    }

    public Reservation modifyPassportId(Long reservationId, Long memberId, String passportId) {
        Reservation reservation = getReservationById(reservationId);
        if (!reservation.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("본인의 예약만 수정할 수 있습니다");
        }
        reservation.setPassportId(passportId);
        save(reservation);
        return reservation;
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteReservationById(id);
    }

}
