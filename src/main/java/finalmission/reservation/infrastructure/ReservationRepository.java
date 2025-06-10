package finalmission.reservation.infrastructure;

import finalmission.member.domain.Member;
import finalmission.reservation.domain.Reservation;
import finalmission.umbrella.domain.Umbrella;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    boolean existsByReservationDateAndMemberAndUmbrella(LocalDate reservationDate, Member member, Umbrella umbrella);

    long countReservationByReservationDateAndUmbrella(LocalDate reservationDate, Umbrella umbrella);
}
