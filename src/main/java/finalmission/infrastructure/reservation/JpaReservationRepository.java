package finalmission.infrastructure.reservation;

import finalmission.domain.member.entity.Member;
import finalmission.domain.reservation.entity.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaReservationRepository extends ListCrudRepository<Reservation, Long> {

    boolean findByLessonAndDateAndTime(final String lesson, final LocalDate date, final LocalTime time);

    List<Reservation> findByMember(final Member member);
}
