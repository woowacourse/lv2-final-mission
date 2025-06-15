package finalmission.planning.ui;

import static finalmission.planning.TestFixture.DEFAULT_PLAN_DATE;
import static finalmission.planning.TestFixture.DEFAULT_TIME_SLOT_1;
import static finalmission.planning.TestFixture.DEFAULT_TIME_SLOT_2;
import static finalmission.planning.TestFixture.DEFAULT_TIME_SLOT_3;
import static finalmission.planning.TestFixture.createNormalUser;
import static finalmission.planning.TestFixture.createNormalUserByName;
import static org.assertj.core.api.Assertions.assertThat;

import finalmission.planning.domain.PlanDate;
import finalmission.planning.domain.Reservation;
import finalmission.planning.domain.ReservationSlot;
import finalmission.planning.domain.TimeSlot;
import finalmission.planning.domain.User;
import finalmission.planning.ui.dto.request.CreateReservationRequest;
import finalmission.planning.ui.dto.response.ReservationResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ReservationControllerTest extends IntegrationTest {

    @DisplayName("로그인되어있는 유저가 예약슬롯을 선택하여 예약 등록이 가능하다.")
    @Test
    void createReservation() {
        // given
        User user = dbHelper.insertUser(createNormalUser());
        String token = jwtTokenProvider.createToken(user.getId(), user.getRole());

        PlanDate planDate = DEFAULT_PLAN_DATE;
        TimeSlot timeSlot = DEFAULT_TIME_SLOT_1;
        ReservationSlot reservationSlot = dbHelper.insertReservationSlot(
                new ReservationSlot(planDate, timeSlot));

        CreateReservationRequest createReservationRequest = new CreateReservationRequest(reservationSlot.getId());

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

    @DisplayName("현재 로그인되어있는 유저의 예약목록을 모두 조회한다.")
    @Test
    void getReservationsByUser() {
        // given
        User user = dbHelper.insertUser(createNormalUserByName("멍구"));
        User other = dbHelper.insertUser(createNormalUserByName("다른 유저"));
        String token = jwtTokenProvider.createToken(user.getId(), user.getRole());

        PlanDate planDate = DEFAULT_PLAN_DATE;
        ReservationSlot reservationSlot1 = dbHelper.insertReservationSlot(
                new ReservationSlot(planDate, DEFAULT_TIME_SLOT_1));
        ReservationSlot reservationSlot2 = dbHelper.insertReservationSlot(
                new ReservationSlot(planDate, DEFAULT_TIME_SLOT_2));
        ReservationSlot reservationSlot3 = dbHelper.insertReservationSlot(
                new ReservationSlot(planDate, DEFAULT_TIME_SLOT_3));

        dbHelper.insertReservation(new Reservation(user, reservationSlot1));
        dbHelper.insertReservation(new Reservation(user, reservationSlot2));
        dbHelper.insertReservation(new Reservation(other, reservationSlot3));

        // when
        List<ReservationResponse> responses = RestAssured.given().log().all()
                .cookie("token", token)
                .when().get("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath().getList(".", ReservationResponse.class);

        // then
        assertThat(responses).hasSize(2);
    }

}
