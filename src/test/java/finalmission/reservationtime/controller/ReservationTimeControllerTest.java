package finalmission.reservationtime.controller;

import finalmission.fixture.MemberFixture;
import finalmission.reservationtime.dto.ReservationTimeRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalTime;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReservationTimeControllerTest {

    @Test
    void 관리자가_예약_시간을_추가한다() {
        Map<String, String> cookies = MemberFixture.loginAdmin();
        ReservationTimeRequest request = new ReservationTimeRequest(LocalTime.of(17, 0));

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .body(request)
                .when().post("/admin/reservationTimes")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 사용자가_예약_시간을_추가하면_예외가_발생한다() {
        Map<String, String> cookies = MemberFixture.loginUser();
        ReservationTimeRequest request = new ReservationTimeRequest(LocalTime.of(17, 0));

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .body(request)
                .when().post("/admin/reservationTimes")
                .then().log().all()
                .statusCode(401);
    }

    @Test
    void 사용자가_예약_시간을_조회한다() {
        Map<String, String> cookies = MemberFixture.loginUser();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .when().get("/reservationTimes")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 사용자가_이용_가능한_예약_시간을_조회한다() {
        Map<String, String> cookies = MemberFixture.loginUser();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .queryParam("date", "2026-12-06")
                .queryParam("roomId", 3)
                .when().get("/reservationTimes/available")
                .then().log().all()
                .statusCode(200);
    }
}
