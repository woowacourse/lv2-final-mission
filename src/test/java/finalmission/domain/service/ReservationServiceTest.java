package finalmission.domain.service;

import finalmission.domain.entity.Gender;
import finalmission.domain.entity.LessonTime;
import finalmission.domain.entity.Member;
import finalmission.domain.entity.Reservation;
import finalmission.domain.entity.ReservationStatus;
import finalmission.domain.entity.Trainer;
import finalmission.domain.service.dto.TrainerReservationResponse;
import finalmission.repository.LessonTimeRepository;
import finalmission.repository.MemberRepository;
import finalmission.repository.ReservationRepository;
import finalmission.repository.TrainerRepository;
import finalmission.util.AbstractServiceTest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

class ReservationServiceTest extends AbstractServiceTest {

    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private LessonTimeRepository lessonTimeRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReservationService reservationService;

    @DisplayName("담당선생님의 예약 현황을 확인한다")
    @Test
    @Transactional
    void getReservationsByTrainer() {
        // given
        Trainer trainer = Trainer.createWithoutId("상희", Gender.WOMAN);
        Trainer savedTrainer = trainerRepository.save(trainer);

        Member member = Member.createWithoutId("user", "user@email.com", "1234", 2);
        Member saveedMember = memberRepository.save(member);

        LessonTime lessonTime = LessonTime.createWithoutId(LocalTime.of(11, 0));
        LessonTime savedLessonTime = lessonTimeRepository.save(lessonTime);

        Reservation reservation = Reservation.createWithoutId(
                ReservationStatus.RESERVED,
                LocalDate.now().plusDays(1),
                saveedMember,
                savedLessonTime,
                savedTrainer
        );
        reservationRepository.save(reservation);

        // when
        List<TrainerReservationResponse> reservations = reservationService.getTrainersReservations(
                savedTrainer.getId());

        // then
        Assertions.assertThat(reservations).hasSize(1);
        Assertions.assertThat(reservations.getFirst().date()).isAfter(LocalDate.now());
    }
}