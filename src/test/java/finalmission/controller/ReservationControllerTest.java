package finalmission.controller;

import finalmission.domain.Member;
import finalmission.dto.MakingReservationRequest;
import finalmission.dto.MakingReservationTimeRequest;
import finalmission.dto.MakingRoomRequest;
import finalmission.dto.SignUpInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class ReservationControllerTest {

    Member member = new Member(1L, "minki", "naver@", "password");
    String jwt;

    @BeforeEach
    void before() {
        MakingReservationTimeRequest makingReservationTimeRequest = new MakingReservationTimeRequest(LocalTime.now());
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(makingReservationTimeRequest)
                .post("/reservation_time").then()
                .log().all();

        MakingRoomRequest makingRoomRequest = new MakingRoomRequest("room1", 1L);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON).body(makingRoomRequest)
                .when().post("/room").then()
                .log().all();

        SignUpInfo signUpInfo = new SignUpInfo("minki", "naver@", "password");
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpInfo)
                .when().post("/signup")
                .then().log().all();
        String secret = "since-jjwt-api-0.11-secret-key-must-be-longer-than-32-bytes";
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        jwt = Jwts.builder().setSubject(member.getId().toString()).signWith(secretKey).compact();

    }

    @DisplayName("사용자 예약 테스트")
    @Test
    void reservationTest() {
        // given
        MakingReservationRequest makingReservationRequest = new MakingReservationRequest(LocalDate.now(), 1L, 1L);
        System.out.println("testStart");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookies("tocken", jwt)
                .body(makingReservationRequest)
                .when().post("/reservation")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
        // when

        // then
    }
}
