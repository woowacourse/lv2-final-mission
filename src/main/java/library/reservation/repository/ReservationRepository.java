package library.reservation.repository;

import java.util.List;
import java.util.Optional;
import library.reservation.domain.Reservation;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    List<Reservation> findByMemberId(Long id);
    
    Optional<Reservation> findById(Long id);
    
    void delete(Reservation reservation);

    List<Reservation> findAll();
}
