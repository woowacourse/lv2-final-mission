package finalmission.presentation.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

import finalmission.infrastructure.JwtTokenProvider;
import finalmission.presentation.dto.ReservationRequest;
import finalmission.presentation.dto.ReservationResponse;
import finalmission.presentation.dto.YogaSessionForBookingResponse;
import io.restassured.RestAssured;
import java.util.List;
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

    @DisplayName("사용자 본인은 자신이 한 예약의 상세 정보를 확인할 수 있다.")
    @Test
    @Sql("/test-get-yoga-sessions-for-booking-data.sql")
    void getMyReservations() {
        //given
        String token = jwtTokenProvider.createToken(String.valueOf(1));
        jdbcTemplate.update("INSERT INTO reservation (member_id, session_id) VALUES (1, 2)");

        //when & then
        var reservations = RestAssured.given().log().all()
                .cookie("token", token)
                .when().get("/reservations/mine")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(2))
                .extract().body().jsonPath()
                .getList(".", ReservationResponse.class);

        var reservation1 = reservations.getFirst();
        var reservation2 = reservations.getLast();

        assertAll(
                () -> assertThat(reservation1.memberDto().id()).isEqualTo(1),
                () -> assertThat(reservation1.sessionServiceResponse().sessionId()).isEqualTo(1),
                () -> assertThat(reservation1.sessionServiceResponse().courseName()).isEqualTo("아쉬탕가베이직"),
                () -> assertThat(reservation2.memberDto().id()).isEqualTo(1),
                () -> assertThat(reservation2.sessionServiceResponse().sessionId()).isEqualTo(2),
                () -> assertThat(reservation2.sessionServiceResponse().courseName()).isEqualTo("빈야사")
        );
    }
}
