package finalmission.reservation.repository;

import finalmission.concert.domain.Concert;
import finalmission.reservation.controller.dto.ReservationDetailResponse;
import finalmission.reservation.domain.Reservation;
import finalmission.seat.domain.Seat;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(Long id);

    List<Reservation> findAll();

    List<Reservation> findByMemberId(Long memberId);

    List<ReservationDetailResponse> findDetailsByMemberId(Long memberId);

    boolean existsByConcertAndSeat(Concert concert, Seat seat);

    void delete(Reservation reservation);
}
