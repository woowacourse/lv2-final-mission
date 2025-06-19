package finalmission.presentation.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

import finalmission.infrastructure.JwtTokenProvider;
import finalmission.presentation.dto.ReservationRequest;
import finalmission.presentation.dto.YogaSessionForBookingResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("특정 날짜의 요가 세션 조회 시 현재 예약자 수와 추가 예약 가능 여부를 함께 응답한다.")
    @Test
    @Sql("/test-get-yoga-sessions-for-booking-data.sql")
    void getYogaSessionsForBooking() {
        //given
        int sessionCount = jdbcTemplate.queryForObject("SELECT count(*) FROM yoga_session", Integer.class);

        //when & then
        var sessions = RestAssured.given().log().all()
                .param("date", "2025-07-01")
                .when().get("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath()
                .getList(".", YogaSessionForBookingResponse.class);

        assertAll(
                () -> assertThat(sessions).hasSize(sessionCount),
                () -> assertThat(sessions.getFirst().isFullBooking()).isTrue(),
                () -> assertThat(sessions.getFirst().currentAttendance()).isEqualTo(8L),
                () -> assertThat(sessions.getLast().isFullBooking()).isFalse(),
                () -> assertThat(sessions.getLast().currentAttendance()).isEqualTo(1L)
        );
    }

    @DisplayName("로그인된 사용자는 요가 세션을 예약할 수 있다.")
    @Test
    @Sql("/test-get-yoga-sessions-for-booking-data.sql")
    void registerReservation() {
        //given
        String token = jwtTokenProvider.createToken(String.valueOf(1));
        var request = new ReservationRequest(2);

        int reservationCount = jdbcTemplate.queryForObject("SELECT count(*) FROM reservation", Integer.class);

        assertThat(reservationCount).isEqualTo(9);

        int expectedId = reservationCount + 1;

        //when & then
        var response = RestAssured.given().log().all()
                .cookie("token", token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("id", is(expectedId));

    }
}
