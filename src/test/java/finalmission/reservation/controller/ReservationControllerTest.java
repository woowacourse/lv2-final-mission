package finalmission.reservation.controller;

import finalmission.member.dto.SigninRequest;
import finalmission.reservation.dto.EditRequest;
import finalmission.reservation.dto.ReservationRequest;
import io.restassured.RestAssured;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @Test
    @DisplayName("내 예약을 삭제한다")
    public void test3() {
        String cookie = RestAssured.given().log().all()
                .body(new SigninRequest("1@a.com", "wooteco7"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().header("Set-Cookie").split(";")[0];

        RestAssured.given().log().all()
                .header("Cookie", cookie)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);
    }

    @Test
    @DisplayName("다른 사용자의 예약을 삭제할 수 없다")
    public void test4() {
        String cookie = RestAssured.given().log().all()
                .body(new SigninRequest("1@a.com", "wooteco7"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().header("Set-Cookie").split(";")[0];

        RestAssured.given().log().all()
                .header("Cookie", cookie)
                .when().delete("/reservations/2")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("새 예약을 생성한다")
    public void test5() {
        String cookie = RestAssured.given().log().all()
                .body(new SigninRequest("1@a.com", "wooteco7"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().header("Set-Cookie").split(";")[0];

        ReservationRequest request = new ReservationRequest(1L, LocalDate.now(), "페어프로그래밍", LocalTime.now());

        RestAssured.given().log().all()
                .header("Cookie", cookie)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("내 예악 정보를 수정한다")
    public void test6() {
        String cookie = RestAssured.given().log().all()
                .body(new SigninRequest("1@a.com", "wooteco7"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().header("Set-Cookie").split(";")[0];

        RestAssured.given().log().all()
                .header("Cookie", cookie)
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(200)
                .body("get(\"room\").get(\"id\")", is(1));

        EditRequest request = new EditRequest(3L, LocalDate.now(), "페어프로그래밍", LocalTime.now());

        RestAssured.given().log().all()
                .header("Cookie", cookie)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/reservations/1")
                .then().log().all()
                .statusCode(200)
                .body("get(\"room\").get(\"id\")", is(3));
    }

    @Test
    @DisplayName("다른 사용자의 예약을 수정할 수 없다")
    public void test7() {
        String cookie = RestAssured.given().log().all()
                .body(new SigninRequest("1@a.com", "wooteco7"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all().extract().header("Set-Cookie").split(";")[0];

        EditRequest request = new EditRequest(3L, LocalDate.now(), "페어프로그래밍", LocalTime.now());

        RestAssured.given().log().all()
                .header("Cookie", cookie)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/reservations/2")
                .then().log().all()
                .statusCode(400);
    }
}
