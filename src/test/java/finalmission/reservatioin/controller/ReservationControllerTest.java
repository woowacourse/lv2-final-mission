package finalmission.reservatioin.controller;

import static finalmission.reservatioin.entity.ReservationTime.LUNCH;
import static org.hamcrest.Matchers.is;

import finalmission.customer.controller.dto.request.TokenLoginCreateRequest;
import finalmission.customer.entity.Customer;
import finalmission.customer.repository.CustomerJpaRepository;
import finalmission.customer.service.AuthService;
import finalmission.omakase.entity.Omakase;
import finalmission.omakase.entity.Rating;
import finalmission.omakase.repository.OmakaseJpaRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CustomerJpaRepository customerJpaRepository;

    @Autowired
    private OmakaseJpaRepository omakaseJpaRepository;

    @Autowired
    private AuthService authService;

    private Map<String, Object> reservation;

    private Customer customer = new Customer("neo", "woowaNeo", "neo@com", "1234");

    private Omakase omakase = new Omakase("sushiJun", Rating.HIGH_END);

    private String token;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        customerJpaRepository.save(customer);
        omakaseJpaRepository.save(omakase);

        token = authService.tokenLogin(new TokenLoginCreateRequest("neo@com", "1234")).token();

        reservation = new HashMap<>();
        reservation.put("customerName", "neo");
        reservation.put("omakaseStoreName", "sushiJun");
        reservation.put("reservationDate", "2025-06-15");
        reservation.put("reservationTime", LUNCH);
    }

    @Test
    @DisplayName("예약을 생성한다.")
    void saveTest() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(reservation)
                .when().post("reservations")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void deleteTest() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(reservation)
                .when().post("reservations")
                .then().log().all()
                .statusCode(201);

        RestAssured.given().log().all()
                .cookie("token", token)
                .when().delete("reservations/1")
                .then().log().all()
                .statusCode(204);
    }

    @Test
    @DisplayName("본인의 예약을 조회한다.")
    void getMyReservationsTest() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(reservation)
                .when().post("reservations")
                .then().log().all()
                .statusCode(201);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .when().get("reservations/mine")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @DisplayName("현재 예약 현황을 조회한다.")
    void getCurrentStateOfReservationTest() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(reservation)
                .when().post("reservations")
                .then().log().all()
                .statusCode(201);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("reservations")
                .then().log().all()
                .statusCode(200)
                .body("[0].numberOfReservation", is(1))
                .body("[0].storeName", is("sushiJun"))
                .body("[0].date", is("2025-06-15"))
                .body("[0].time", is("LUNCH"));
    }

}
