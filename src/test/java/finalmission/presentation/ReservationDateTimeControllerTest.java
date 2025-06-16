package finalmission.presentation;

import static org.hamcrest.Matchers.is;

import finalmission.TestConfig;
import finalmission.application.dto.ReservationDateTimeIdRequest;
import finalmission.application.dto.ReservationDateTimeRequest;
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
public class ReservationDateTimeControllerTest {

    @Test
    @DisplayName("시간 추가 테스트")
    void createReservationDateTimeTest(){
        // given
        ReservationDateTimeRequest reservationDateTimeRequest = new ReservationDateTimeRequest(
                1L, LocalDateTime.of(2025, 6, 16, 12, 30)
        );

        // when - then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationDateTimeRequest)
                .when().post("/dateTimes")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("시간 조회 테스트")
    void getTimesTest() {
        // given
        ReservationDateTimeRequest reservationDateTimeRequest = new ReservationDateTimeRequest(
                1L, LocalDateTime.of(2025, 6, 16, 12, 30)
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationDateTimeRequest)
                .when().post("/dateTimes")
                .then().log().all()
                .statusCode(201);

        // when-then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/dateTimes?nickname=포비")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @DisplayName("시간 삭제 테스트")
    void deleteTimesTest() {
        // given
        ReservationDateTimeRequest reservationDateTimeRequest = new ReservationDateTimeRequest(
                1L, LocalDateTime.of(2025, 6, 16, 12, 30)
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationDateTimeRequest)
                .when().post("/dateTimes")
                .then().log().all()
                .statusCode(201);

        ReservationDateTimeIdRequest reservationDateTimeIdRequest = new ReservationDateTimeIdRequest(1L);

        // when-then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationDateTimeIdRequest)
                .when().delete("/dateTimes/1")
                .then().log().all()
                .statusCode(204);
    }
}
