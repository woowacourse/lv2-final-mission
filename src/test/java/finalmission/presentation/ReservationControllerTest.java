package finalmission.presentation;

import static org.hamcrest.Matchers.is;

import finalmission.TestConfig;
import finalmission.application.dto.ReservationDateTimeRequest;
import finalmission.application.dto.ReservationRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Import(TestConfig.class)
public class ReservationControllerTest {

    @Test
    @DisplayName("예약 추가 테스트")
    void createReservationTest(){
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 16, 12, 30);

        ReservationDateTimeRequest reservationDateTimeRequest = new ReservationDateTimeRequest(
                1L, dateTime
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationDateTimeRequest)
                .when().post("/dateTimes")
                .then().log().all();

        ReservationRequest reservationRequest = new ReservationRequest(1L, "포비", dateTime);

        // when - then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("예약 조회 테스트")
    void getReservationsTest() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 16, 12, 30);

        ReservationDateTimeRequest reservationDateTimeRequest = new ReservationDateTimeRequest(
                1L, dateTime
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationDateTimeRequest)
                .when().post("/dateTimes")
                .then().log().all();

        ReservationRequest reservationRequest = new ReservationRequest(1L, "포비", dateTime);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationRequest)
                .when().post("/reservations")
                .then().log().all();

        // when - then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/reservations?crew=링크")
                .then().log().all()
                .body("size()", is(1));
    }

    @Test
    @DisplayName("예약 수정 테스트")
    void putReservationsTest() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 16, 12, 30);

        ReservationDateTimeRequest reservationDateTimeRequest = new ReservationDateTimeRequest(
                1L, dateTime
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationDateTimeRequest)
                .when().post("/dateTimes")
                .then().log().all();

        ReservationRequest reservationRequest = new ReservationRequest(1L, "포비", dateTime);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationRequest)
                .when().post("/reservations")
                .then().log().all();

        // when
        reservationRequest = new ReservationRequest(1L, "리사", dateTime);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationRequest)
                .when().put("/reservations/1")
                .then().log().all()
                .statusCode(200);

        // then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/reservations?crew=링크")
                .then().log().all()
                .body("size()", is(1));
    }

    @Test
    @DisplayName("예약 삭제 테스트")
    void deleteReservationsTest() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 16, 12, 30);

        ReservationDateTimeRequest reservationDateTimeRequest = new ReservationDateTimeRequest(
                1L, dateTime
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationDateTimeRequest)
                .when().post("/dateTimes")
                .then().log().all();

        ReservationRequest reservationRequest = new ReservationRequest(1L, "포비", dateTime);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationRequest)
                .when().post("/reservations")
                .then().log().all();

        // when - then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);
    }
}
