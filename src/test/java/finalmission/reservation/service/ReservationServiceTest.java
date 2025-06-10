package finalmission.reservation.service;

import finalmission.member.domain.Member;
import finalmission.member.domain.vo.Role;
import finalmission.reservation.service.dto.CreateReservationInformationRequest;
import finalmission.restaurant.domain.Restaurant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({ReservationService.class})
class ReservationServiceTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ReservationService reservationService;

    @DisplayName("식당 소유자가 아닌 경우, 예약 가능 정보를 추가할 수 없다.")
    @Test
    void cannotCreateReservationInformationByNotOwner() {
        // given
        Member owner = new Member(Role.OWNER, "진짜주인", "real@test.com", "12341234");
        Member fakeOwner = new Member(Role.OWNER, "사기꾼", "fake@test.com", "12341234");

        Restaurant restaurant = new Restaurant("진짜식당", owner);

        entityManager.persist(owner);
        entityManager.persist(fakeOwner);
        entityManager.persist(restaurant);

        CreateReservationInformationRequest request = new CreateReservationInformationRequest(
                restaurant.getId(),
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 0),
                5
        );

        // when & then
        assertThatThrownBy(() -> {
            reservationService.createReservationInformation(request, fakeOwner.getId());
        }).isInstanceOf(IllegalArgumentException.class);
     }
}
