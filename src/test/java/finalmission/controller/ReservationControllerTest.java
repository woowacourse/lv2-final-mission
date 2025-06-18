package finalmission.controller;

import finalmission.auth.controller.dto.request.LoginRequest;
import finalmission.member.controller.dto.request.MemberRequest;
import finalmission.reservation.controller.dto.request.ReservationRequest;
import finalmission.restaurant.controller.dto.request.RestaurantRequest;
import finalmission.reservationTime.controller.dto.request.ReservationTimeRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReservationControllerTest {


    private MemberRequest memberRequest;
    private LoginRequest loginRequest;
    private RestaurantRequest restaurantRequest;
    private ReservationTimeRequest timeRequest;
    private ReservationRequest createReservationRequest;
    private ReservationRequest updateReservationRequest;

    @BeforeEach
    public void setUp() {
        memberRequest = new MemberRequest("ywcsuwon@naver.com", "123123", "Lemon");
        loginRequest = new LoginRequest("123123", "ywcsuwon@naver.com");
        restaurantRequest = new RestaurantRequest("모수","모던 한식 파인다이닝", "서울시 이태원로", 50555,24);
        timeRequest = new ReservationTimeRequest("17:00");
        createReservationRequest = new ReservationRequest(1, 1, LocalDate.of(2025, 5, 8), 5);
        updateReservationRequest = new ReservationRequest(1, 1, LocalDate.of(2025, 5, 9), 10);
    }

    @Test
    @DisplayName("예약 생성 테스트")
    public void test1() {
        RestAssured.given().log().all()
                .contentType("application/json")
                .body(memberRequest)
                .when().post("/member")
                .then()
                .statusCode(HttpStatus.CREATED.value());


        String token = RestAssured.given().log().all()
                .contentType("application/json")
                .body(loginRequest)
                .when().post("/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().cookie("token");

        RestAssured.given().log().all()
                .contentType("application/json")
                .body(restaurantRequest)
                .when().post("/restaurant")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given().log().all()
                .contentType("application/json")
                .body(timeRequest)
                .when().post("/time")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given().log().all()
                .contentType("application/json")
                .cookie("token",token)
                .body(createReservationRequest)
                .when().post("/reservation")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("예약 수정 테스트")
    public void test2() {
        RestAssured.given().log().all()
                .contentType("application/json")
                .body(memberRequest)
                .when().post("/member")
                .then()
                .statusCode(HttpStatus.CREATED.value());


        String token = RestAssured.given().log().all()
                .contentType("application/json")
                .body(loginRequest)
                .when().post("/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().cookie("token");

        RestAssured.given().log().all()
                .contentType("application/json")
                .body(restaurantRequest)
                .when().post("/restaurant")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given().log().all()
                .contentType("application/json")
                .body(timeRequest)
                .when().post("/time")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given().log().all()
                .contentType("application/json")
                .cookie("token",token)
                .body(createReservationRequest)
                .when().post("/reservation")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given().log().all()
                .contentType("application/json")
                .cookie("token",token)
                .body(updateReservationRequest)
                .when().put("/reservation/1")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("예약 삭제 테스트")
    public void test3() {
        RestAssured.given().log().all()
                .contentType("application/json")
                .body(memberRequest)
                .when().post("/member")
                .then()
                .statusCode(HttpStatus.CREATED.value());


        String token = RestAssured.given().log().all()
                .contentType("application/json")
                .body(loginRequest)
                .when().post("/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().cookie("token");

        RestAssured.given().log().all()
                .contentType("application/json")
                .body(restaurantRequest)
                .when().post("/restaurant")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given().log().all()
                .contentType("application/json")
                .body(timeRequest)
                .when().post("/time")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given().log().all()
                .contentType("application/json")
                .cookie("token",token)
                .body(createReservationRequest)
                .when().post("/reservation")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given().log().all()
                .contentType("application/json")
                .cookie("token",token)
                .when().delete("/reservation/1")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
