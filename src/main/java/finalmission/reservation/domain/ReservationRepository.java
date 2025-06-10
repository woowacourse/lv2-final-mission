package finalmission.reservation.domain;

import java.util.List;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<Reservation> findAll();
}
