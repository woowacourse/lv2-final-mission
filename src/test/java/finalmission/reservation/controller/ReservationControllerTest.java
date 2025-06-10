package finalmission.reservation.controller;

import finalmission.member.dto.SigninRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Test
    @DisplayName("모든 예약 목록을 조회한다")
    public void test1() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    @DisplayName("내 예약 상세 정보를 조회한다")
    public void test2() {
        String cookie = RestAssured.given().log().all()
                .body(new SigninRequest("1@a.com", "wooteco7"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().header("Set-Cookie").split(";")[0];

        RestAssured.given().log().all()
                .header("Cookie", cookie)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(200);
    }
}
