package finalmission.controller;

import finalmission.auth.controller.dto.request.LoginRequest;
import finalmission.controller.member.controller.dto.request.MemberRequest;
import finalmission.controller.reservation.controller.dto.request.ReservationRequest;
import finalmission.controller.restaurant.controller.dto.request.RestaurantRequest;
import finalmission.reservationTime.controller.dto.request.ReservationTimeRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
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
    private ReservationRequest reservationRequest;
    @BeforeEach
    public void setUp() {
        memberRequest = new MemberRequest("ywcsuwon@naver.com", "123123", "Lemon");
        loginRequest = new LoginRequest("123123", "ywcsuwon@naver.com");
        restaurantRequest = new RestaurantRequest("모수","모던 한식 파인다이닝", "서울시 이태원로", 50555,24);
        timeRequest = new ReservationTimeRequest("17:00");
        reservationRequest = new ReservationRequest(1, 1, LocalDate.of(2025, 5, 8), 5);
    }

    @Test
    public void test() {
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
                .body(reservationRequest)
                .when().post("/reservation")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }
}
