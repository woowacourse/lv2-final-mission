package finalmission.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import finalmission.common.client.PublicHolidayClient;
import finalmission.domain.entity.Manager;
import finalmission.domain.entity.Member;
import finalmission.domain.entity.Reservation;
import finalmission.domain.entity.Tour;
import finalmission.domain.vo.MemberRole;
import finalmission.dto.ReservationCreateRequest;
import finalmission.dto.ReservationDetailResponse;
import finalmission.dto.ReservationResponse;
import finalmission.dto.ReservationUpdateRequest;
import finalmission.repository.ManagerRepository;
import finalmission.repository.MemberRepository;
import finalmission.repository.ReservationRepository;
import finalmission.repository.TourRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    ManagerRepository managerRepository;
    @Mock
    TourRepository tourRepository;
    @Mock
    ReservationRepository reservationRepository;
    @Mock
    PublicHolidayClient publicHolidayClient;
    @InjectMocks
    ReservationService reservationService;

    @Test
    void findAllMemberReservationsTest() {
        // given
        Reservation reservation1 = createReservationByReservationIdAndMemberId(1L, 1L);
        Reservation reservation2 = createReservationByReservationIdAndMemberId(2L, 1L);
        when(reservationRepository.findByMemberId(1L)).thenReturn(List.of(reservation1, reservation2));

        // when
        List<ReservationResponse> responses = reservationService.findAllMemberReservations(1L);

        // then
        assertThat(responses.size()).isEqualTo(2);
    }

    @Test
    void findMemberReservationDetailTest() {
        // given
        Reservation reservation = createReservationByReservationIdAndMemberId(1L, 1L);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        // when
        ReservationDetailResponse response = reservationService.findMemberReservationDetail(1L);

        // then
        assertThat(response).isNotNull();
        assertThat(response).isInstanceOf(ReservationDetailResponse.class);
    }

    @DisplayName("조회된 예약이 없을 경우 예외를 발생한다.")
    @Test
    void throwErrorWhenReservationNotFoundTest() {
        // given
        when(reservationRepository.findById(2L)).thenReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> reservationService.findMemberReservationDetail(2L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Reservation not found");
    }

    @DisplayName("조회된 멤버가 없을 경우 예외를 발생한다.")
    @Test
    void throwErrorWhenMemberNotFoundTest() {
        // given
        when(memberRepository.findById(2L)).thenReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> reservationService.createMemberReservation(2L,
                new ReservationCreateRequest(1L, 1L, LocalDate.now(), LocalTime.now())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Member not found");
    }

    @DisplayName("조회된 담당자가 없을 경우 예외를 발생한다.")
    @Test
    void throwErrorWhenManagerNotFoundTest() {
        // given
        when(memberRepository.findById(2L)).thenReturn(
                Optional.of(new Member(1L, "member", "member@email.com", "Password123!@#", MemberRole.USER)));
        when(managerRepository.findById(1L)).thenReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> reservationService.createMemberReservation(2L,
                new ReservationCreateRequest(1L, 1L, LocalDate.now(), LocalTime.now())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Manager not found");
    }

    @DisplayName("조회된 투어가 없을 경우 예외를 발생한다.")
    @Test
    void throwErrorWhenTourNotFoundTest() {
        // given
        when(memberRepository.findById(2L)).thenReturn(
                Optional.of(new Member(1L, "member", "member@email.com", "Password123!@#", MemberRole.USER)));
        when(managerRepository.findById(1L)).thenReturn(Optional.of(new Manager(1L, "Peter", "010-1234-5678")));
        when(tourRepository.findById(1L)).thenReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> reservationService.createMemberReservation(2L,
                new ReservationCreateRequest(1L, 1L, LocalDate.now(), LocalTime.now())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Tour not found");
    }

    @Test
    void createMemberReservationTest() {
        // given
        when(memberRepository.findById(1L)).thenReturn(
                Optional.of(new Member(1L, "member", "member@email.com", "Password123!@#", MemberRole.USER)));
        when(managerRepository.findById(1L)).thenReturn(Optional.of(new Manager(1L, "Peter", "010-1234-5678")));
        when(tourRepository.findById(1L)).thenReturn(Optional.of(new Tour(1L, "Peter", "010-1234-5678")));
        when(reservationRepository.save(any())).thenReturn(new Reservation(1L,
                new Member(1L, "member", "member@email.com", "Password123!@#", MemberRole.USER),
                new Manager(1L, "Peter", "010-1234-5678"),
                new Tour(1L, "Peter", "010-1234-5678"),
                LocalDate.now(),
                LocalTime.now()
        ));

        // when
        ReservationResponse response = reservationService.createMemberReservation(1L,
                new ReservationCreateRequest(1L, 1L, LocalDate.now(), LocalTime.now()));

        // then
        assertThat(response).isNotNull();
        assertThat(response).isInstanceOf(ReservationResponse.class);
    }

    @Test
    void updateMemberReservationTest() {
        // given
        Reservation reservation = createReservationByReservationIdAndMemberId(1L, 1L);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        // when
        ReservationResponse response = reservationService.updateMemberReservation(1L,
                new ReservationUpdateRequest("member2@email.com"));

        // then
        assertThat(response).isNotNull();
        assertThat(response).isInstanceOf(ReservationResponse.class);
    }

    @Test
    void deleteMemberReservationTest() {
        // when
        reservationService.deleteMemberReservation(1L);

        // then
        verify(reservationRepository, atLeastOnce()).deleteById(1L);
    }

    private Reservation createReservationByReservationIdAndMemberId(Long reservationId, Long memberId) {
        return new Reservation(
                reservationId,
                new Member(memberId, "member", "member1@email.com", "Password123!@#", MemberRole.USER),
                new Manager(1L, "Peter", "010-1234-5678"),
                new Tour(1L, "title", "description"),
                LocalDate.of(2025, 6, 10),
                LocalTime.of(10, 30)
        );
    }
}
