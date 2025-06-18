package finalmission.reservation.controller;

import finalmission.fixture.MemberFixture;
import finalmission.reservation.dto.ReservationRequest;
import finalmission.reservation.dto.ReservationUpdateRequest;
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

    @Test
    void 예약을_수정한다() {
        Map<String, String> cookies = MemberFixture.loginUser();
        ReservationUpdateRequest request = new ReservationUpdateRequest(LocalDate.of(2026, 12, 7), 2L, 4L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .body(request)
                .when().patch("/reservations/2")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 다른_사용자가_예약_수정_시_예외가_발생한다() {
        Map<String, String> cookies = MemberFixture.loginUser();
        ReservationUpdateRequest request = new ReservationUpdateRequest(LocalDate.of(2026, 12, 7), 2L, 4L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .body(request)
                .when().patch("/reservations/5")
                .then().log().all()
                .statusCode(401);
    }

    @Test
    void 존재하지_않는_예약을_삭제할_경우_예외가_발생한다() {
        Map<String, String> cookies = MemberFixture.loginUser();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .when().delete("/reservations/999")
                .then().log().all()
                .statusCode(404);
    }

    @Test
    void 존재하지_않는_회의실을_예약할_경우_예외가_발생한다() {
        Map<String, String> cookies = MemberFixture.loginUser();
        LocalDate localDate = LocalDate.of(2026, 10, 7);
        ReservationRequest request = new ReservationRequest(localDate, 1L, 999L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(404);
    }
}
