package finalmission.reservation.presentation;

import finalmission.reservation.presentation.dto.request.TimeCreateWebRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalTime;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TimeControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 시간을_생성하는_요청을_보내고_성공_시_201을_반환한다() {
        LocalTime startAt = LocalTime.now().plusMinutes(1);

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new TimeCreateWebRequest(startAt))
                .when()
                    .post("/reservations/times")
                .then()
                    .statusCode(201)
                    .body("startAt", containsString(String.format("%02d:%02d:%02d", startAt.getHour(), startAt.getMinute(), startAt.getSecond())));

    }

    @Test
    void 저장되어_있는_모든_시간을_조회하는_요청을_보내고_성공_시_200을_반환한다() {
        LocalTime startAt1 = LocalTime.now().plusMinutes(1);
        LocalTime startAt2 = LocalTime.now().plusMinutes(10);

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new TimeCreateWebRequest(startAt1))
                .when()
                    .post("/reservations/times")
                .then()
                    .statusCode(201);
        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new TimeCreateWebRequest(startAt2))
                .when()
                    .post("/reservations/times")
                .then()
                    .statusCode(201);

        RestAssured
                .when()
                    .get("reservations/times")
                .then()
                    .statusCode(200)
                    .body("", hasSize(2));
    }
}