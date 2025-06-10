package finalmission.infrastructure;

import finalmission.domain.Member;
import finalmission.domain.Reservation;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.Repository;

public interface ReservationRepository extends Repository<Reservation, Long> {
    Reservation save(Reservation reservation)
            ;

    List<Reservation> findByToiletIdAndDate(Long toiletId, LocalDate date);

    List<Reservation> findByMember(Member member);
}
