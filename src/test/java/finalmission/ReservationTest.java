package finalmission;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTest {

    @Test
    @DisplayName("예약 가능 시간을 조회한다")
    void getAvailableTime() {
        RestAssured.given()
            .when().get("/reservations?meetingroom=1&date=2025-06-10")
            .then()
            .statusCode(200)
            .body("size()", is(9));
    }

    @Test
    @DisplayName("예약이 가능하다")
    void createReservation() {
        Map<String, Object> params = new HashMap<>();
        params.put("meetingRoomId", 1L);
        params.put("timeId", 1L);
        params.put("date", LocalDate.of(2025, 06, 11));

        RestAssured.given()
            .cookies("token", TestFixture.login())
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(200);
    }

    @Test
    @DisplayName("이미 예약이 존재한다면 예약할 수 없다")
    void cantCreateReservationIfDuplicated() {
        Map<String, Object> params = new HashMap<>();
        params.put("meetingRoomId", 1L);
        params.put("timeId", 1L);
        params.put("date", LocalDate.of(2025, 06, 11));

        RestAssured.given()
            .cookies("token", TestFixture.login())
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(200);

        RestAssured.given()
            .cookies("token", TestFixture.login())
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(500);
        //TODO: 에러 핸들러 작성 후 500으로 수정
    }

}
