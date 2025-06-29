package finalmission.reservation.domain;

import finalmission.member.domain.Member;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    void deleteById(Long id);

    boolean existsByReservationSlot(ReservationSlot reservationSlot);

    Reservation getById(Long reservationId);

    Optional<Reservation> findByReservationSlot(ReservationSlot reservationSlot);

    List<Reservation> findAll();

    List<Reservation> findAllByMember(Member member);
}
