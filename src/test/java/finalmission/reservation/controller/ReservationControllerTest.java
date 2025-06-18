package finalmission.reservation.controller;

import static org.hamcrest.Matchers.is;

import finalmission.auth.dto.LoginRequest;
import finalmission.reservation.dto.ReservationRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class ReservationControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("예약을 생성할 수 있다.")
    @Test
    void create() {
        ReservationRequest request = new ReservationRequest(LocalDate.now(), LocalDate.now().plusDays(3), 1L);
        String token = getToken();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);
    }

    @DisplayName("내 예약을 조회할 수 있다.")
    @Test
    void findAllMyReservations() {
        String token = getToken();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .when().get("/reservations/mine")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("내 예약을 삭제할 수 있다.")
    @Test
    void deleteMyReservation() {
        String token = getToken();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .when().delete("/reservations/mine/1")
                .then().log().all()
                .statusCode(204);
    }

    @DisplayName("내 예약을 수정할 수 있다.")
    @Test
    void updateMyReservation() {
        ReservationRequest request = new ReservationRequest(LocalDate.of(2025, 9, 15), LocalDate.of(2025, 9, 18), 1L);
        String token = getToken();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)
                .when().patch("/reservations/mine/1")
                .then().log().all()
                .statusCode(201)
                .body("checkInDate", is("2025-09-15"));
    }

    private String getToken() {
        LoginRequest request = new LoginRequest("haru@woowa.com", "aaa");

        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/login")
                .then().log().all()
                .extract()
                .cookie("token");
    }
}