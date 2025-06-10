package finalmission.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import finalmission.auth.JwtProvider;
import finalmission.reservation.domain.Reservation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {

    private final int port;
    private final JdbcTemplate jdbcTemplate;
    private final JwtProvider jwtProvider;

    public ReservationControllerTest(
            @LocalServerPort final int port,
            @Autowired JdbcTemplate jdbcTemplate,
            @Autowired JwtProvider jwtProvider) {
        this.port = port;
        this.jdbcTemplate = jdbcTemplate;
        this.jwtProvider = jwtProvider;
    }

    @DisplayName("예약 생성 테스트 with 쿠키 토큰")
    @Test
    void createReservationTest() {
        // given
        String phoneNumber = "01012345678";
        givenMember(phoneNumber);
        String token = jwtProvider.provideToken(phoneNumber);

        Map<String, Object> body = Map.ofEntries(
                Map.entry("date", "2023-10-01"),
                Map.entry("subway_number", 1),
                Map.entry("seat", "A"),
                Map.entry("departStation", "강남"),
                Map.entry("arriveStation", "잠실새내")
        );

        Reservation reservation = RestAssured
                .given()
                .port(port)
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(body)

                .when()
                .post("/reservations")

                .then()
                .statusCode(201)
                .extract().body().as(Reservation.class);

        assertThat(reservation.getMember().getId()).isEqualTo(1L);
        assertThat(reservation.getMember().getPhoneNumber()).isEqualTo(phoneNumber);
    }


    @DisplayName("이상한 토큰으로 예약 생성 시도시 실패")
    @Test
    void createReservationTest2() {
        // given
        String phoneNumber = "01012345678";
        givenMember(phoneNumber);

        Map<String, Object> body = Map.ofEntries(
                Map.entry("date", "2023-10-01"),
                Map.entry("subway_number", 1),
                Map.entry("seat", "A"),
                Map.entry("departStation", "강남"),
                Map.entry("arriveStation", "잠실새내")
        );

        RestAssured
                .given()
                .port(port)
                .contentType(ContentType.JSON)
                .cookie("token", "fakeToken")
                .body(body)

                .when()
                .post("/reservations")

                .then()
                .statusCode(500);
    }

    private void givenMember(String phoneNumber) {
        final String sql = "INSERT INTO member (phone_number) VALUES ('" + phoneNumber + "')";
        jdbcTemplate.update(sql);
    }
}
