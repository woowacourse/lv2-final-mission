package finalmission.unit.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.exception.member.MemberNotFoundException;
import finalmission.exception.reservation.NotMyReservationException;
import finalmission.exception.reservation.ReservationNotFoundException;
import finalmission.exception.toilet.ToiletNotFoundException;
import finalmission.member.domain.Member;
import finalmission.member.infrastructure.MemberRepository;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.dto.request.ReservationRequest;
import finalmission.reservation.dto.response.ReservationResponse;
import finalmission.reservation.infrastructure.ReservationRepository;
import finalmission.reservation.service.ReservationService;
import finalmission.toilet.domain.Toilet;
import finalmission.toilet.infrastructure.ToiletRepository;
import finalmission.unit.fake.FakeMemberRepository;
import finalmission.unit.fake.FakeReservationRepository;
import finalmission.unit.fake.FakeToiletRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class ReservationServiceTest {

    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository = new FakeReservationRepository();
    private final MemberRepository memberRepository = new FakeMemberRepository();
    private final ToiletRepository toiletRepository = new FakeToiletRepository();

    public ReservationServiceTest() {
        this.reservationService = new ReservationService(
                reservationRepository, memberRepository, toiletRepository
        );
    }

    @Test
    void 예약을_생성한다() {
        // given
        Member member = memberRepository.save(new Member("email1@domain.com", "nickname1", "password"));
        Toilet toilet = toiletRepository.save(new Toilet("position1"));
        ReservationRequest request = new ReservationRequest(LocalDate.now().plusDays(1), LocalTime.of(9, 0),
                LocalTime.of(10, 0), toilet.getId());
        // when
        ReservationResponse response = reservationService.createReservation(member.getId(), request);
        // then
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(response.date()).isEqualTo(request.date());
        soft.assertThat(response.startAt()).isEqualTo(request.startAt());
        soft.assertThat(response.endAt()).isEqualTo(request.endAt());
        soft.assertThat(response.member().id()).isEqualTo(member.getId());
        soft.assertThat(response.toilet().id()).isEqualTo(toilet.getId());
        soft.assertAll();
    }

    @Test
    void 존재하지_않는_회원으로_예약을_생성하면_예외가_발생한다() {
        // given
        Toilet toilet = toiletRepository.save(new Toilet("position1"));
        ReservationRequest request = new ReservationRequest(LocalDate.now().plusDays(1), LocalTime.of(9, 0),
                LocalTime.of(10, 0), toilet.getId());
        // when & then
        assertThatThrownBy(() -> reservationService.createReservation(1L, request))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void 존재하지_않는_화장실에_예약을_생성하면_예외가_발생한다() {
        // given
        Member member = memberRepository.save(new Member("email1@domain.com", "nickname1", "password"));
        ReservationRequest request = new ReservationRequest(LocalDate.now().plusDays(1), LocalTime.of(9, 0),
                LocalTime.of(10, 0), 1L);
        // when & then
        assertThatThrownBy(() -> reservationService.createReservation(member.getId(), request))
                .isInstanceOf(ToiletNotFoundException.class);
    }

    @Test
    void 특정_날짜와_화장실에_해당하는_예약을_조회한다() {
        // given
        Member member = memberRepository.save(new Member("email1@domain.com", "nickname1", "password"));
        Toilet toilet1 = toiletRepository.save(new Toilet("position1"));
        Toilet toilet2 = toiletRepository.save(new Toilet("position2"));
        LocalDate date = LocalDate.now().plusDays(1);
        reservationRepository.save(new Reservation(date, LocalTime.of(8, 0), LocalTime.of(9, 0), member, toilet1));
        reservationRepository.save(
                new Reservation(date.plusDays(1), LocalTime.of(8, 0), LocalTime.of(9, 0), member, toilet1));
        reservationRepository.save(new Reservation(date, LocalTime.of(8, 0), LocalTime.of(9, 0), member, toilet2));
        // when
        List<ReservationResponse> reservations = reservationService.findReservations(toilet1.getId(), date);
        // then
        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0).date()).isEqualTo(date);
        assertThat(reservations.get(0).toilet().id()).isEqualTo(toilet1.getId());
    }

    @Test
    void 특정_회원의_예약을_조회한다() {
        // given
        Member member1 = memberRepository.save(new Member("email1@domain.com", "nickname1", "password"));
        Member member2 = memberRepository.save(new Member("email2@domain.com", "nickname2", "password"));
        Toilet toilet = toiletRepository.save(new Toilet("position1"));
        LocalDate date = LocalDate.now().plusDays(1);
        reservationRepository.save(new Reservation(date, LocalTime.of(8, 0), LocalTime.of(9, 0), member1, toilet));
        reservationRepository.save(new Reservation(date, LocalTime.of(8, 0), LocalTime.of(9, 0), member2, toilet));
        // when
        List<ReservationResponse> reservations = reservationService.findReservationsByMemberId(member1.getId());
        // then
        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0).member().id()).isEqualTo(member1.getId());
    }

    @Test
    void 존재하지_않는_회원의_예약을_조회하면_예외가_발생한다() {
        // given
        toiletRepository.save(new Toilet("position1"));
        // when & then
        assertThatThrownBy(() -> reservationService.findReservationsByMemberId(1L))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void 예약을_삭제한다() {
        // given
        Member member = memberRepository.save(new Member("email1@domain.com", "nickname1", "password"));
        Toilet toilet = toiletRepository.save(new Toilet("position1"));
        LocalDate date = LocalDate.now().plusDays(1);
        Reservation reservation = reservationRepository.save(
                new Reservation(date, LocalTime.of(8, 0), LocalTime.of(9, 0), member, toilet));
        // when
        assertThatCode(() -> reservationService.deleteReservationById(member.getId(), reservation.getId()))
                .doesNotThrowAnyException();
    }

    @Test
    void 존재하지_않는_예약을_삭제하면_예외가_발생한다() {
        // given
        Member member = memberRepository.save(new Member("email1@domain.com", "nickname1", "password"));
        toiletRepository.save(new Toilet("position1"));
        // when
        assertThatThrownBy(() -> reservationService.deleteReservationById(member.getId(), 1L))
                .isInstanceOf(ReservationNotFoundException.class);
    }

    @Test
    void 다른_회원의_예약을_삭제하면_예외가_발생한다() {
        // given
        Member member1 = memberRepository.save(new Member("email1@domain.com", "nickname1", "password"));
        Member member2 = memberRepository.save(new Member("email2@domain.com", "nickname2", "password"));
        Toilet toilet = toiletRepository.save(new Toilet("position1"));
        LocalDate date = LocalDate.now().plusDays(1);
        Reservation reservation = reservationRepository.save(
                new Reservation(date, LocalTime.of(8, 0), LocalTime.of(9, 0), member1, toilet));
        // when
        assertThatThrownBy(() -> reservationService.deleteReservationById(member2.getId(), reservation.getId()))
                .isInstanceOf(NotMyReservationException.class);
    }
}