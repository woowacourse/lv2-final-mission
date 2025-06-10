package woowaTable.reservation.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowaTable.reservation.domain.Reservation;
import woowaTable.reservation.domain.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;

    public Reservation save(final Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}
