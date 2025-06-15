package finalmission.reservation.service;

import finalmission.member.domain.Member;
import finalmission.member.domain.vo.Role;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationInformation;
import finalmission.reservation.service.dto.CreateReservationInformationRequest;
import finalmission.reservation.service.dto.CreateReservationRequest;
import finalmission.reservation.service.dto.ReservationDetailResponse;
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
        }).isInstanceOf(IllegalStateException.class);
    }
}
