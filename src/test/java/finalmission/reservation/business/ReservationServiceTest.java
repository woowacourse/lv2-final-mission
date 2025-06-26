package finalmission.reservation.business;

import finalmission.holiday.database.HolidayRepository;
import finalmission.holiday.model.Holiday;
import finalmission.medical.model.TreatmentType;
import finalmission.member.database.MemberRepository;
import finalmission.member.model.Member;
import finalmission.reservation.business.dto.request.ReservationCreateRequest;
import finalmission.reservation.business.dto.request.ReservationDeleteRequest;
import finalmission.reservation.business.dto.request.ReservationDetailedGetRequest;
import finalmission.reservation.business.dto.request.ReservationUpdateTreatmentTypeRequest;
import finalmission.reservation.database.TimeRepository;
import finalmission.reservation.model.Reservation;
import finalmission.reservation.model.Time;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Transactional
class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;

    @Autowired
    TimeRepository timeRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    HolidayRepository holidayRepository;

    @Test
    void 예약을_생성할_수_있다() {
        // Given
        Time time = timeRepository.save(new Time(LocalTime.now().plusMinutes(1)));
        Member member = memberRepository.save(new Member("username", "password", "프리"));
        ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest(TreatmentType.EXTRACTION, LocalDate.now().plusDays(1), time.getId(), member.getUsername());

        // When
        Reservation reservation = reservationService.createReservation(reservationCreateRequest);

        // Then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(reservation).isNotNull();
            softAssertions.assertThat(reservation.getTime()).isEqualTo(time);
            softAssertions.assertThat(reservation.getMember()).isEqualTo(member);
        });
    }

    @Test
    void 존재하지_않는_시간_id로는_예약할_수_없다() {
        // Given
        Member member = memberRepository.save(new Member("username", "password", "프리"));
        ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest(TreatmentType.EXTRACTION, LocalDate.now().plusDays(1), 500L, member.getUsername());

        // When & Then
        assertThatThrownBy(() -> reservationService.createReservation(reservationCreateRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 시간 id입니다.");
    }

    @Test
    void 존재하지_않는_멤버_id로는_예약할_수_없다() {
        // Given

        // When

        // Then
    }

    @Test
    void 이미_예약되어_있는_시각에_중복으로_예약할_수_없다() {
        // Given
        Time time = timeRepository.save(new Time(LocalTime.now().plusMinutes(1)));
        Member member = memberRepository.save(new Member("username", "password", "프리"));
        ReservationCreateRequest reservationCreateRequest1 = new ReservationCreateRequest(TreatmentType.EXTRACTION, LocalDate.now().plusDays(1), time.getId(), member.getUsername());
        ReservationCreateRequest reservationCreateRequest2 = new ReservationCreateRequest(TreatmentType.EXTRACTION, LocalDate.now().plusDays(1), time.getId(), member.getUsername());
        reservationService.createReservation(reservationCreateRequest1);

        // When & Then
        assertThatThrownBy(() -> reservationService.createReservation(reservationCreateRequest2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 예약된 시각입니다.");
    }

    @Test
    void 공휴일에는_예약할_수_없다() {
        // Given
        LocalDate nationalLiberationDay = LocalDate.of(2025, 8, 15);
        holidayRepository.save(new Holiday(nationalLiberationDay, "광복절"));
        Time time = timeRepository.save(new Time(LocalTime.now().plusMinutes(1)));
        Member member = memberRepository.save(new Member("username", "password", "프리"));
        ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest(TreatmentType.EXTRACTION, nationalLiberationDay, time.getId(), member.getUsername());

        // When & Then
        assertThatThrownBy(() -> reservationService.createReservation(reservationCreateRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("진료하지 않는 날짜입니다.");
    }

    @Test
    void 저장된_모든_예약을_조회할_수_있다() {
        // Given
        Time time = timeRepository.save(new Time(LocalTime.now().plusMinutes(1)));
        Member member = memberRepository.save(new Member("username", "password", "프리"));
        ReservationCreateRequest reservationCreateRequest1 = new ReservationCreateRequest(TreatmentType.EXTRACTION, LocalDate.now().plusDays(1), time.getId(), member.getUsername());
        ReservationCreateRequest reservationCreateRequest2 = new ReservationCreateRequest(TreatmentType.EXTRACTION, LocalDate.now().plusDays(2), time.getId(), member.getUsername());
        Reservation reservation1 = reservationService.createReservation(reservationCreateRequest1);
        Reservation reservation2 = reservationService.createReservation(reservationCreateRequest2);

        // When & Then
        assertThat(reservationService.findAllReservations())
                .containsExactlyInAnyOrder(reservation1, reservation2);
    }

    @Test
    void 주어진_기간_사이의_예약을_모두_조회할_수_있다() {
        // Given
        Time time = timeRepository.save(new Time(LocalTime.now().plusMinutes(1)));
        Member member = memberRepository.save(new Member("username", "password", "프리"));
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusDays(2);
        ReservationCreateRequest reservationCreateRequest1 = new ReservationCreateRequest(TreatmentType.EXTRACTION, startDate, time.getId(), member.getUsername());
        ReservationCreateRequest reservationCreateRequest2 = new ReservationCreateRequest(TreatmentType.EXTRACTION, startDate.plusDays(1), time.getId(), member.getUsername());
        ReservationCreateRequest reservationCreateRequest3 = new ReservationCreateRequest(TreatmentType.EXTRACTION, startDate.plusDays(2), time.getId(), member.getUsername());
        ReservationCreateRequest reservationCreateRequest4 = new ReservationCreateRequest(TreatmentType.EXTRACTION, startDate.plusDays(3), time.getId(), member.getUsername());
        Reservation reservation1 = reservationService.createReservation(reservationCreateRequest1);
        Reservation reservation2 = reservationService.createReservation(reservationCreateRequest2);
        Reservation reservation3 = reservationService.createReservation(reservationCreateRequest3);
        reservationService.createReservation(reservationCreateRequest4);

        // When & Then
        assertThat(reservationService.findReservationOfPeriod(startDate, endDate))
                .containsExactlyInAnyOrder(reservation1, reservation2, reservation3);
    }

    @Test
    void 주어진_멤버의_이름으로_저장된_예약을_모두_확인할_수_있다() {
        // Given
        Time time = timeRepository.save(new Time(LocalTime.now().plusMinutes(1)));
        Member member = memberRepository.save(new Member("username", "password", "프리"));
        Member differentMember = memberRepository.save(new Member("username2", "password", "다른 사람"));
        ReservationCreateRequest reservationCreateRequest1 = new ReservationCreateRequest(TreatmentType.EXTRACTION, LocalDate.now().plusDays(1), time.getId(), member.getUsername());
        ReservationCreateRequest reservationCreateRequest2 = new ReservationCreateRequest(TreatmentType.EXTRACTION, LocalDate.now().plusDays(2), time.getId(), member.getUsername());
        ReservationCreateRequest reservationCreateRequest3 = new ReservationCreateRequest(TreatmentType.EXTRACTION, LocalDate.now().plusDays(3), time.getId(), differentMember.getUsername());
        Reservation reservation1 = reservationService.createReservation(reservationCreateRequest1);
        Reservation reservation2 = reservationService.createReservation(reservationCreateRequest2);
        reservationService.createReservation(reservationCreateRequest3);

        // When & Then
        assertThat(reservationService.findMemberReservations(member.getName()))
                .containsExactlyInAnyOrder(reservation1, reservation2);
    }

    @Test
    void 존재하지_않는_멤버_이름으로는_멤버의_예약을_가져올_수_없다() {
        // Given
        // When
        // Then
        assertThatThrownBy(() -> reservationService.findMemberReservations("존재하지 않는 멤버 이름"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 멤버의 이름입니다.");
    }

    @Test
    void 주어진_멤버의_예약을_id로_가져온다() {
        // Given
        Time time = timeRepository.save(new Time(LocalTime.now().plusMinutes(1)));
        Member member = memberRepository.save(new Member("username", "password", "프리"));
        ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest(TreatmentType.EXTRACTION, LocalDate.now().plusDays(1), time.getId(), member.getUsername());
        Reservation reservation = reservationService.createReservation(reservationCreateRequest);
        ReservationDetailedGetRequest reservationDetailedGetRequest = new ReservationDetailedGetRequest(reservation.getId(), member.getUsername());

        // When & Then
        assertThat(reservationService.findDetailedReservationOfMember(reservationDetailedGetRequest)).isEqualTo(reservation);
    }

    @Test
    void 잘못된_예약_id로는_주어진_멤버의_예약을_가져올_수_없다() {
        // Given
        Time time = timeRepository.save(new Time(LocalTime.now().plusMinutes(1)));
        Member member = memberRepository.save(new Member("username", "password", "프리"));
        ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest(TreatmentType.EXTRACTION, LocalDate.now().plusDays(1), time.getId(), member.getUsername());
        reservationService.createReservation(reservationCreateRequest);
        ReservationDetailedGetRequest reservationDetailedGetRequest = new ReservationDetailedGetRequest(500L, member.getUsername());

        // When & Then
        assertThatThrownBy(() -> reservationService.findDetailedReservationOfMember(reservationDetailedGetRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 예약 id입니다.");
    }

    @Test
    void 주어진_멤버의_예약이_아니라면_예약을_조회할_수_없다() {
        // Given
        Time time = timeRepository.save(new Time(LocalTime.now().plusMinutes(1)));
        Member member = memberRepository.save(new Member("username", "password", "프리"));
        ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest(TreatmentType.EXTRACTION, LocalDate.now().plusDays(1), time.getId(), member.getUsername());
        Reservation reservation = reservationService.createReservation(reservationCreateRequest);
        ReservationDetailedGetRequest reservationDetailedGetRequest = new ReservationDetailedGetRequest(reservation.getId(), "다른 사람");

        // When & Then
        assertThatThrownBy(() -> reservationService.findDetailedReservationOfMember(reservationDetailedGetRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 멤버의 예약이 아닙니다.");
    }

    @Test
    void 예약되어_있는_진료의_진료_종류를_바꿀_수_있다() {
        // Given
        Time time = timeRepository.save(new Time(LocalTime.now().plusMinutes(1)));
        Member member = memberRepository.save(new Member("username", "password", "프리"));
        ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest(TreatmentType.EXTRACTION, LocalDate.now().plusDays(1), time.getId(), member.getUsername());
        Reservation reservation = reservationService.createReservation(reservationCreateRequest);
        ReservationUpdateTreatmentTypeRequest reservationUpdateTreatmentTypeRequest = new ReservationUpdateTreatmentTypeRequest(reservation.getId(), TreatmentType.SCALING, member.getUsername());

        // When
        Reservation changedReservation = reservationService.changeTreatmentType(reservationUpdateTreatmentTypeRequest);

        // Then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(changedReservation).isNotNull();
            softAssertions.assertThat(changedReservation.getTreatmentType()).isEqualTo(TreatmentType.SCALING);
            softAssertions.assertThat(changedReservation.getMember()).isEqualTo(member);
        });
    }

    @Test
    void 존재하지_않는_예약_id로는_진료_종류를_변경할_수_없다() {
        // Given
        Time time = timeRepository.save(new Time(LocalTime.now().plusMinutes(1)));
        Member member = memberRepository.save(new Member("username", "password", "프리"));
        ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest(TreatmentType.EXTRACTION, LocalDate.now().plusDays(1), time.getId(), member.getUsername());
        Reservation reservation = reservationService.createReservation(reservationCreateRequest);
        ReservationUpdateTreatmentTypeRequest reservationUpdateTreatmentTypeRequest = new ReservationUpdateTreatmentTypeRequest(500L, TreatmentType.SCALING, member.getUsername());

        // When & Then
        assertThatThrownBy(() -> reservationService.changeTreatmentType(reservationUpdateTreatmentTypeRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 예약 id입니다.");
    }

    @Test
    void 주어진_멤버의_예약이_아니라면_진료_종류를_변경할_수_없다() {
        // Given
        Time time = timeRepository.save(new Time(LocalTime.now().plusMinutes(1)));
        Member member = memberRepository.save(new Member("username", "password", "프리"));
        ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest(TreatmentType.EXTRACTION, LocalDate.now().plusDays(1), time.getId(), member.getUsername());
        Reservation reservation = reservationService.createReservation(reservationCreateRequest);
        ReservationUpdateTreatmentTypeRequest reservationUpdateTreatmentTypeRequest = new ReservationUpdateTreatmentTypeRequest(reservation.getId(), TreatmentType.SCALING, "다른 사람");

        // When & Then
        assertThatThrownBy(() -> reservationService.changeTreatmentType(reservationUpdateTreatmentTypeRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 멤버의 예약이 아닙니다.");
    }
    
    @Test
    void 저장된_예약을_삭제할_수_있다() {
        // Given
        Time time = timeRepository.save(new Time(LocalTime.now().plusMinutes(1)));
        Member member = memberRepository.save(new Member("username", "password", "프리"));
        ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest(TreatmentType.EXTRACTION, LocalDate.now().plusDays(1), time.getId(), member.getUsername());
        Reservation reservation = reservationService.createReservation(reservationCreateRequest);
        ReservationDeleteRequest reservationDeleteRequest = new ReservationDeleteRequest(reservation.getId(), member.getUsername());
        int originalCount = reservationService.findAllReservations().size();
        
        // When
        reservationService.deleteById(reservationDeleteRequest);
    
        // Then
        assertThat(reservationService.findAllReservations().size()).isEqualTo(originalCount - 1);
    }
    
    @Test
    void 존재하지_않는_예약_id로는_예약을_삭제할_수_없다() {
        // Given
        Time time = timeRepository.save(new Time(LocalTime.now().plusMinutes(1)));
        Member member = memberRepository.save(new Member("username", "password", "프리"));
        ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest(TreatmentType.EXTRACTION, LocalDate.now().plusDays(1), time.getId(), member.getUsername());
        reservationService.createReservation(reservationCreateRequest);
        ReservationDeleteRequest reservationDeleteRequest = new ReservationDeleteRequest(500L, member.getUsername());

        // When & Then
        assertThatThrownBy(() -> reservationService.deleteById(reservationDeleteRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 예약 id입니다.");
    }
    
    @Test
    void 주어진_멤버의_예약이_아니라면_예약을_삭제할_수_없다() {
        // Given
        Time time = timeRepository.save(new Time(LocalTime.now().plusMinutes(1)));
        Member member = memberRepository.save(new Member("username", "password", "프리"));
        ReservationCreateRequest reservationCreateRequest = new ReservationCreateRequest(TreatmentType.EXTRACTION, LocalDate.now().plusDays(1), time.getId(), member.getUsername());
        Reservation reservation = reservationService.createReservation(reservationCreateRequest);
        ReservationDeleteRequest reservationDeleteRequest = new ReservationDeleteRequest(reservation.getId(), "다른 사람");

        // When & Then
        assertThatThrownBy(() -> reservationService.deleteById(reservationDeleteRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 멤버의 예약이 아닙니다.");
    }
}