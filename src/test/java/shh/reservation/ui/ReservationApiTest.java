package shh.reservation.ui;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import shh.login.application.TokenCookieService;
import shh.login.application.dto.LoginRequest;
import shh.reservation.application.dto.ReservationAddRequest;
import shh.reservation.application.dto.ReservationUpdateRequest;
import shh.reservation.domain.ReservationTime;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReservationApiTest {

    @LocalServerPort
    private int port;
    private String token;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        final String email = "test1@email.com";
        final String password = "1234";

        final LoginRequest request = new LoginRequest(email, password);

        token = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .header(HttpHeaders.SET_COOKIE)
                .split(";")[0]
                .split(TokenCookieService.COOKIE_TOKEN_KEY + "=")[1];
    }


    @Test
    void 예약을_추가한다() {
        final ReservationAddRequest request = new ReservationAddRequest(
                LocalDate.now().plusDays(1),
                1L,
                1L
        );

        RestAssured.given().log().all()
                .cookie(TokenCookieService.COOKIE_TOKEN_KEY, token)
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    void 본인의_예약을_조회한다() {
        RestAssured.given().log().all()
                .cookie(TokenCookieService.COOKIE_TOKEN_KEY, token)
                .contentType(ContentType.JSON)
                .when().get("/mine")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(4));
    }

    @Test
    void 본인의_예약을_수정한다() {
        final ReservationUpdateRequest request = new ReservationUpdateRequest(
                LocalDate.now(),
                new ReservationTime(5L, LocalTime.of(11, 0))
        );

        RestAssured.given().log().all()
                .cookie(TokenCookieService.COOKIE_TOKEN_KEY, token)
                .contentType(ContentType.JSON)
                .body(request)
                .when().put("/reservations/1")
                .then().log().all()
                .statusCode(200);
    }
}
