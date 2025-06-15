package finalmission.reservation.service;

import finalmission.member.domain.Member;
import finalmission.member.domain.vo.Role;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationInformation;
import finalmission.reservation.service.dto.request.CreateReservationInformationRequest;
import finalmission.reservation.service.dto.request.CreateReservationRequest;
import finalmission.reservation.service.dto.request.UpdateReservationRequest;
import finalmission.reservation.service.dto.response.ReservationDetailResponse;
import finalmission.reservation.service.dto.response.ReservationResponse;
import finalmission.restaurant.domain.Restaurant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({ReservationService.class})
class ReservationServiceTest {

    public final Member ME = new Member(Role.CUSTOMER, "me", "me@test.com", "12341234");
    private final Member OWNER = new Member(Role.OWNER, "진짜주인", "real@test.com", "12341234");
    private final Restaurant RESTAURANT = new Restaurant("진짜식당", OWNER);
    private final ReservationInformation RESERVATION_INFORMATION = new ReservationInformation(RESTAURANT, LocalDate.now().plusDays(1), LocalTime.of(10, 0), 5);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ReservationService reservationService;

    @BeforeEach
    void setup() {
        entityManager.persist(ME);
        entityManager.persist(OWNER);
        entityManager.persist(RESTAURANT);
        entityManager.persist(RESERVATION_INFORMATION);
    }

    @DisplayName("존재하지 않는 예약 정보로 예약을 생성할 수 없다.")
    @Test
    void cannotCreateReservationWithNotExistInformation() {
        // given
        CreateReservationRequest request = new CreateReservationRequest(100L);

        // when & then
        assertThatThrownBy(() -> {
            reservationService.createReservation(request, ME.getId());
        }).isInstanceOf(IllegalArgumentException.class);
     }

    @DisplayName("식당 소유자가 아닌 경우, 예약 가능 정보를 추가할 수 없다.")
    @Test
    void cannotCreateReservationInformationByNotOwner() {
        // given
        Member fakeOwner = new Member(Role.OWNER, "사기꾼", "fake@test.com", "12341234");

        entityManager.persist(fakeOwner);

        CreateReservationInformationRequest request = new CreateReservationInformationRequest(
                RESTAURANT.getId(),
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 0),
                5
        );

        // when & then
        assertThatThrownBy(() -> {
            reservationService.createReservationInformation(request, fakeOwner.getId());
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("자신의 예약 상세 정보를 열람할 수 있다.")
    @Test
    void getMyReservationDetails() {
        // given
        Reservation reservation = new Reservation(RESERVATION_INFORMATION, ME);

        entityManager.persist(reservation);

        // when
        ReservationDetailResponse response = reservationService.getMyReservationDetail(ME.getId(), reservation.getId());

        // then
        assertThat(response.restaurant()).isEqualTo(RESERVATION_INFORMATION.getRestaurant().getName());
        assertThat(response.date()).isEqualTo(RESERVATION_INFORMATION.getDate());
        assertThat(response.time()).isEqualTo(RESERVATION_INFORMATION.getStartAt());
    }

    @DisplayName("예약 생성자가 아닌 경우, 예약 상세 정보를 열람할 수 없다.")
    @Test
    void cannotGetReservationDetailsByOther() {
        // given
        Member other = new Member(Role.CUSTOMER, "other", "other@test.com", "12341234");

        Reservation reservation = new Reservation(RESERVATION_INFORMATION, ME);

        entityManager.persist(other);
        entityManager.persist(reservation);

        // when & then
        assertThatThrownBy(() -> {
            reservationService.getMyReservationDetail(other.getId(), reservation.getId());
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예약 생성자는 자신의 예약을 삭제할 수 있다.")
    @Test
    void deleteMyReservation() {
        // given
        Reservation reservation = new Reservation(RESERVATION_INFORMATION, ME);

        entityManager.persist(reservation);

        final Long reservationId = reservation.getId();

        // when
        reservationService.deleteReservation(ME.getId(), reservationId);

        // then
        assertThat(entityManager.find(Reservation.class, reservationId)).isNull();
    }

    @DisplayName("다른 사람의 예약을 삭제할 수 없다.")
    @Test
    void cannotDeleteOtherReservation() {
        // given
        Member other = new Member(Role.CUSTOMER, "other", "other@test.com", "12341234");
        Reservation reservation = new Reservation(RESERVATION_INFORMATION, ME);

        entityManager.persist(other);
        entityManager.persist(reservation);

        // when & then
        assertThatThrownBy(() -> {
            reservationService.deleteReservation(other.getId(), reservation.getId());
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예약 생성자는 자신의 예약 정보를 수정할 수 있다.")
    @Test
    void updateMyReservation() {
        // given
        Reservation reservation = new Reservation(RESERVATION_INFORMATION, ME);
        ReservationInformation updateInformation = new ReservationInformation(RESTAURANT, LocalDate.now().plusDays(1), LocalTime.of(11, 0), 5);

        entityManager.persist(reservation);
        entityManager.persist(updateInformation);

        UpdateReservationRequest request = new UpdateReservationRequest(updateInformation.getId());

        // when
        reservationService.updateReservation(reservation.getId(), request, ME.getId());

        // then
        Reservation updated = entityManager.find(Reservation.class, reservation.getId());
        assertThat(updated.getReservationInformation().getId()).isEqualTo(updateInformation.getId());
    }

    @DisplayName("다른 사람의 예약은 수정할 수 없다.")
    @Test
    void cannotUpdateOtherReservation() {
        // given
        Member other = new Member(Role.CUSTOMER, "other", "other@test.com", "12341234");
        Reservation reservation = new Reservation(RESERVATION_INFORMATION, ME);
        ReservationInformation updateInformation = new ReservationInformation(RESTAURANT, LocalDate.now().plusDays(1), LocalTime.of(11, 0), 5);

        entityManager.persist(other);
        entityManager.persist(reservation);
        entityManager.persist(updateInformation);

        UpdateReservationRequest request = new UpdateReservationRequest(updateInformation.getId());

        // when & then
        assertThatThrownBy(() -> {
            reservationService.updateReservation(reservation.getId(), request, other.getId());
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
