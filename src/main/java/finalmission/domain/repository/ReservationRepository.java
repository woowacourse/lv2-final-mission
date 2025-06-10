package finalmission.domain.repository;

import finalmission.domain.entity.LessonTime;
import finalmission.domain.entity.Member;
import finalmission.domain.entity.Reservation;
import finalmission.domain.entity.ReservationStatus;
import finalmission.domain.entity.Trainer;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByTrainerAndDateAfterOrderByDateAsc(Trainer trainer, LocalDate localDate);

    boolean existsByDateAndLessonTimeAndTrainer(LocalDate date, LessonTime lessonTimeId, Trainer trainerId);

    List<Reservation> findByMemberAndStatus(Member member, ReservationStatus reservationStatus);
}
