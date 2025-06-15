package finalmission.planning.ui;

import static finalmission.planning.TestFixture.DEFAULT_PLAN_DATE;
import static finalmission.planning.TestFixture.DEFAULT_TIME_SLOT;
import static org.assertj.core.api.Assertions.assertThat;

import finalmission.planning.domain.PlanDate;
import finalmission.planning.domain.ReservationSlot;
import finalmission.planning.domain.TimeSlot;
import finalmission.planning.domain.User;
import finalmission.planning.domain.UserRole;
import finalmission.planning.ui.dto.request.CreateReservationRequest;
import finalmission.planning.ui.dto.response.ReservationResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;

class ReservationControllerTest extends IntegrationTest {

    @DisplayName("로그인되어있는 유저가 예약슬롯을 선택하여 예약 등록이 가능하다.")
    @Test
    void createReservation() {
        // given
        User user = dbHelper.insertUser(new User("멍구", "test@email.com", "password", UserRole.NORMAL));
        String token = jwtTokenProvider.createToken(user.getId(), user.getRole());

        PlanDate planDate = DEFAULT_PLAN_DATE;
        TimeSlot timeSlot = DEFAULT_TIME_SLOT;
        ReservationSlot reservationSlot = dbHelper.insertReservationSlot(
                new ReservationSlot(planDate, timeSlot));

        CreateReservationRequest createReservationRequest = new CreateReservationRequest(reservationSlot.getId());

        System.out.println("🔍 트랜잭션 열림 여부 = " + TransactionSynchronizationManager.isActualTransactionActive());
        // when
        ReservationResponse response = RestAssured.given().log().all()
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .body(createReservationRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().as(ReservationResponse.class);

        // then
        assertThat(response.ownerName()).isEqualTo(user.getName());
        assertThat(response.reservationSlot().date()).isEqualTo(planDate.getDate());
    }

}
