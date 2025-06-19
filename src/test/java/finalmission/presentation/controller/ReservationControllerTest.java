package finalmission.presentation.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import finalmission.presentation.dto.YogaSessionForBookingResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
}
