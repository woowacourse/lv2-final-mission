package finalmission.reservation.controller;

import finalmission.fixture.MemberFixture;
import finalmission.reservation.dto.ReservationRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Test
    void 예약을_생성한다() {
        Map<String, String> cookies = MemberFixture.loginUser();
        LocalDate localDate = LocalDate.of(2026, 10, 7);
        ReservationRequest request = new ReservationRequest(localDate, 1L, 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 멤버의_예약을_모두_조회한다() {
        Map<String, String> cookies = MemberFixture.loginUser();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 예약을_삭제한다() {
        Map<String, String> cookies = MemberFixture.loginUser();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);
    }
}
