package woowaTable.restaurant.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowaTable.restaurant.domain.Reservation;
import woowaTable.restaurant.domain.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;

    public Reservation save(final Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}
