package finalmission.meetingroom.controller;

import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class ReservationControllerTest {

    @DisplayName("회의실 예약 생성 요청을 보낸다.")
    @Test
    void postMeetingRoomReservation() {
        String tokenValue = getUserTokenValue();
        Map<String, Object> reservationParams = Map.of(
                "meetingRoomName", "임팩트룸",
                "reservationDate", LocalDate.now().plusDays(1L),
                "startAt", "10:00",
                "endAt", "11:00"
        );

        RestAssured.given().log().all()
                .cookie("token", tokenValue)
                .contentType(ContentType.JSON)
                .body(reservationParams)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);
    }

    @DisplayName("모든 회의실 예약 현황을 조회한다.")
    @Test
    void getMeetingRoomReservations() {
        String tokenValue = getUserTokenValue();
        Map<String, Object> reservationParams = Map.of(
                "meetingRoomName", "임팩트룸",
                "reservationDate", LocalDate.now().plusDays(1L),
                "startAt", "10:00",
                "endAt", "11:00"
        );

        RestAssured.given().log().all()
                .cookie("token", tokenValue)
                .contentType(ContentType.JSON)
                .body(reservationParams)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);

        RestAssured.given().log().all()
                .cookie("token", tokenValue)
                .contentType(ContentType.JSON)
                .body(reservationParams)
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    private String getUserTokenValue() {
        Map<String, String> loginParams = Map.of("email", "user1@email.com", "password", "1234");

        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginParams)
                .when().post("/login")
                .then()
                .extract().cookie("token");
    }
}
