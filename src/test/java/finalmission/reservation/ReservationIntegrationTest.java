package finalmission.reservation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ReservationIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 예약을_조회할_수_있다() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(7));
    }

    @Test
    void 로그인이_되지_않고_예약을_생성할_수_없다() {
        // given
        Map<String, Object> params = new HashMap<>();
        params.put("name", "이씅연");
        params.put("date", LocalDate.of(2025, 7, 25));
        params.put("timeId", 1);
        params.put("exerciesCourseId", 1);

        // when
        Response response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(401)
                .extract()
                .response();

        String actual = response.asString();
        assertThat(actual).isEqualTo("로그인 되어있지 않습니다.");
    }

    @Test
    void 공휴일에_예약을_생성할_수_없다() {
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("email", "forarium20@gmail.com");
        loginParams.put("password", "1234");

        String token = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginParams)
                .when().post("/login")
                .then().log().all()
                .statusCode(200)
                .extract().cookie("token");

        // given
        Map<String, Object> params = new HashMap<>();
        params.put("name", "이씅연");
        params.put("date", "2025-12-25");
        params.put("timeId", 1);
        params.put("exerciseCourseId", 1);

        // when
        Response response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .extract()
                .response();

        String actual = response.asString();
        assertThat(actual).isEqualTo("공휴일은 예약할 수 없습니다.");
    }

    @Test
    void 예약을_삭제할_수_있다() {
        RestAssured.given().log().all()
                .when().delete("/reservations/4")
                .then().log().all()
                .statusCode(204);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(6));
    }
}
