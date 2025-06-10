package finalmission.e2e;

import finalmission.member.dto.LoginRequest;
import finalmission.member.dto.SignupRequest;
import finalmission.reservation.dto.ReservationCreateRequest;
import finalmission.reservation.dto.ReservationCreateResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import java.time.LocalDateTime;
import org.springframework.http.MediaType;

public class TestFixture {

    public static final String REGULAR_EMAIL = "regular@gmail.com";
    public static final String REGULAR_NAME = "regular";
    public static final String TOKEN = "token";
    public static final String PASSWORD = "password";

    public static String loginRegular() {
        RestAssured.given().log().all()
                .body(new SignupRequest(false, REGULAR_NAME, REGULAR_EMAIL, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/member/signup")
                .then().log().all()
                .statusCode(201)
                .extract()
                .as(new TypeRef<>() {
                });

        return RestAssured.given().log().all()
                .body(new LoginRequest(REGULAR_EMAIL, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/member/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .cookie(TOKEN);
    }

    public static Long makeReservation(final LocalDateTime time, final String description, final Long roomId,
                                       final String token) {
        ReservationCreateResponse reservationCreateResponse = RestAssured.given().log().all()
                .cookie(TOKEN, token)
                .body(new ReservationCreateRequest(time, description, roomId))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .extract()
                .as(ReservationCreateResponse.class);
        return reservationCreateResponse.reservationId();
    }
}
