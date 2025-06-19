package finalmission.domain.service;

import finalmission.domain.entity.Gender;
import finalmission.domain.entity.LessonTime;
import finalmission.domain.entity.Member;
import finalmission.domain.entity.Reservation;
import finalmission.domain.entity.ReservationStatus;
import finalmission.domain.entity.Trainer;
import finalmission.domain.service.dto.ReservationLessonRequest;
import finalmission.domain.service.dto.TrainerReservationResponse;
import finalmission.domain.repository.LessonTimeRepository;
import finalmission.domain.repository.MemberRepository;
import finalmission.domain.repository.ReservationRepository;
import finalmission.domain.repository.TrainerRepository;
import finalmission.util.AbstractServiceTest;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
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
    @Autowired
    private EntityManager em;

    @DisplayName("담당선생님의 예약 현황을 확인한다")
    @Test
    void getReservationsByTrainer() {
        // given
        Trainer savedTrainer = getTrainer();
        Member saveedMember = getMember();
        LessonTime savedLessonTime = getLessonTime();
        Reservation reservation = getReservation(saveedMember, savedLessonTime, savedTrainer);
        reservationRepository.save(reservation);

        // when
        List<TrainerReservationResponse> reservations = reservationService.getTrainersReservations(
                savedTrainer.getId());

        // then
        Assertions.assertThat(reservations).hasSize(1);
        Assertions.assertThat(reservations.getFirst().date()).isAfter(LocalDate.now());
    }

    @DisplayName("내 담당 선생님이 아니면 수업을 예약할 수 없다")
    @Test
    void myTrainerReservationException() {
        // given
        Trainer savedTrainer = getTrainer();
        LessonTime savedLessonTime = getLessonTime();
        Member savedMember = getMember();

        ReservationLessonRequest request = new ReservationLessonRequest(
                savedTrainer.getId(),
                savedLessonTime.getId(),
                LocalDate.now()
        );

        // when
        // then
        Assertions.assertThatThrownBy(() -> reservationService.reserveLesson(savedMember, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 담당 선생님의 수업만 수강할 수 있습니다");
    }

    @DisplayName("수강권이 없는 회원은 수업을 예약할 수 없다")
    @Test
    void ptNumberReservationException() {
        // given
        Trainer savedTrainer = getTrainer();
        LessonTime savedLessonTime = getLessonTime();
        Member savedMember = getMember();
        savedMember.selectTrainer(savedTrainer);
        savedMember.minusPtNumber();
        savedMember.minusPtNumber();
        ReservationLessonRequest request = new ReservationLessonRequest(
                savedTrainer.getId(),
                savedLessonTime.getId(),
                LocalDate.now()
        );
        em.flush();

        // when
        // then
        Assertions.assertThatThrownBy(() -> reservationService.reserveLesson(savedMember, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] PT 수강권이 있어야 수강할 수 있습니다");
    }

    @DisplayName("이미 예약이 존재하면 대기 상태로 예약된다")
    @Test
    void waitingReservation() {
        Trainer savedTrainer = getTrainer();
        LessonTime savedLessonTime = getLessonTime();
        Member savedMember = getMember();
        savedMember.selectTrainer(savedTrainer);

        ReservationLessonRequest request = new ReservationLessonRequest(
                savedTrainer.getId(),
                savedLessonTime.getId(),
                LocalDate.now()
        );
        em.flush();

        // when
        reservationService.reserveLesson(savedMember, request);

        // then
        Assertions.assertThatCode(() -> reservationService.reserveLesson(savedMember, request))
                .doesNotThrowAnyException();
    }

    @DisplayName("정상적으로 예약되면 수강권이 1회 차감된다")
    @Test
    void reservationMinusPtNumber() {
        Trainer savedTrainer = getTrainer();
        LessonTime savedLessonTime = getLessonTime();
        Member savedMember = getMember();
        savedMember.selectTrainer(savedTrainer);
        ReservationLessonRequest request = new ReservationLessonRequest(
                savedTrainer.getId(),
                savedLessonTime.getId(),
                LocalDate.now()
        );
        em.flush();
        int ptNumber = savedMember.getPtNumber();

        // when
        // then
        Assertions.assertThatCode(() -> reservationService.reserveLesson(savedMember, request))
                .doesNotThrowAnyException();
        Assertions.assertThat(savedMember.getPtNumber()).isLessThan(ptNumber);
    }

    @DisplayName("최소 이틀 전 예약 취소할 경우 수강권을 돌려받을 수 있다")
    @Test
    void canCancel() {
        // given
        Trainer savedTrainer = getTrainer();
        LessonTime savedLessonTime = getLessonTime();
        Member savedMember = getMember();
        savedMember.selectTrainer(savedTrainer);
        Reservation reservation = Reservation.createWithoutId(
                ReservationStatus.RESERVED,
                LocalDate.now().plusDays(2),
                savedMember,
                savedLessonTime,
                savedTrainer
        );
        Reservation savedReservation = reservationRepository.save(reservation);
        int ptNumber = savedMember.getPtNumber();

        // when
        reservationService.cancelReservation(savedMember, savedReservation.getId());

        // then
        Assertions.assertThat(savedMember.getPtNumber()).isEqualTo(ptNumber + 1);
    }

    @DisplayName("하루 전부터는 예약을 취소해도 수강권을 돌려받을 수 없다")
    @Test
    void cannotRefundPtNumberCancel() {
        // given
        Trainer savedTrainer = getTrainer();
        LessonTime savedLessonTime = getLessonTime();
        Member savedMember = getMember();
        savedMember.selectTrainer(savedTrainer);
        Reservation reservation = Reservation.createWithoutId(
                ReservationStatus.RESERVED,
                LocalDate.now().plusDays(1),
                savedMember,
                savedLessonTime,
                savedTrainer
        );
        Reservation savedReservation = reservationRepository.save(reservation);
        int ptNumber = savedMember.getPtNumber();

        // when
        reservationService.cancelReservation(savedMember, savedReservation.getId());

        // then
        Assertions.assertThat(savedMember.getPtNumber()).isEqualTo(ptNumber);
    }

    private Member getMember() {
        Member member = Member.createWithoutId("user", "user@email.com", "1234", 2);
        return memberRepository.save(member);
    }

    private LessonTime getLessonTime() {
        LessonTime lessonTime = LessonTime.createWithoutId(LocalTime.of(11, 0));
        return lessonTimeRepository.save(lessonTime);
    }

    private Trainer getTrainer() {
        Trainer trainer = Trainer.createWithoutId("상희", Gender.WOMAN);
        return trainerRepository.save(trainer);
    }

    private Reservation getReservation(Member saveedMember, LessonTime savedLessonTime, Trainer savedTrainer) {
        return Reservation.createWithoutId(
                ReservationStatus.RESERVED,
                LocalDate.now().plusDays(1),
                saveedMember,
                savedLessonTime,
                savedTrainer
        );
    }
}