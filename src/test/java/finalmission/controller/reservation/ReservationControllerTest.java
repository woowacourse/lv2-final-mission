package finalmission.controller.reservation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReservationControllerTest {

    private Map<String, Object> params;

    @BeforeEach
    void setUp() {
        // given
        params = createReservationRequestJsonMap(
                "우가",
                "010-4874-3424",
                "등 수업",
                LocalDate.of(2025, 6, 10),
                LocalTime.of(13, 50)
        );
    }

    @Test
    void 예약_생성_요청_테스트() {
        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 현재_예약_현황_조회_테스트() {
        // given
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/reservations/current-situations")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(1));
    }

    @Test
    void 자신의_예약_정보_조회_테스트() {
        final Long memberId = 1L;
        // given
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        // when & then
        RestAssured.given().log().all()
                .pathParam("memberId", memberId)
                .when().get("/reservations/mine/{memberId}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(1));
    }

    @Test
    void 자신의_예약_정보_삭제_테스트() {
        final Long reservationId = 1L;
        final Long memberId = 1L;

        // given
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        // when
        RestAssured.given().log().all()
                .pathParam("reservationId", reservationId)
                .when().delete("/reservations/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // when & then
        RestAssured.given().log().all()
                .pathParam("memberId", memberId)
                .when().get("/reservations/mine/{memberId}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(0));
    }

    @Test
    void 자신의_예약_정보_수정_테스트() {
        final Map<String, Object> updateParams = updateReservationRequestJsonMap(
                LocalDate.of(2025, 7, 10),
                LocalTime.of(20, 20)
        );
        // given
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(updateParams)
                .pathParam("reservationId", 1)
                .when().patch("/reservations/update/{reservationId}")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(1))
                .body("date", is(LocalDate.of(2025, 7, 10).toString()))
                .body("time", is(LocalTime.of(20, 20).toString()));
    }

    private Map<String, Object> updateReservationRequestJsonMap(
            final LocalDate date,
            final LocalTime time
    ) {
        return Map.of(
                "date", date,
                "time", time
        );
    }

    private Map<String, Object> createReservationRequestJsonMap(
            final String name,
            final String phoneNumber,
            final String lesson,
            final LocalDate date,
            final LocalTime time) {
        return Map.of(
                "name", name,
                "phoneNumber", phoneNumber,
                "lesson", lesson,
                "date", date,
                "time", time
        );
    }
}
