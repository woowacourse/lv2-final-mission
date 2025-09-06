package finalmission.cake.repository;

import finalmission.cake.model.Reservation;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    List<Reservation> findByCakeIdAndDate(Long cakeId, LocalDate date);

    Optional<Reservation> findByCakeIdAndDateAndTimeId(Long cakeId, LocalDate date, Long timeId);

    Reservation save(Reservation reservation);

    List<Reservation> findByMemberId(Long memberId);

    Optional<Reservation> findByIdAndMemberId(Long reservationId, Long memberId);

    void delete(Reservation reservation);
}
