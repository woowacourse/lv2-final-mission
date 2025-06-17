package finalmission.reservation;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import finalmission.helper.TestHelper;
import finalmission.reservation.dto.request.ReservationCreateRequest;
import finalmission.reservation.dto.request.ReservationUpdateRequest;
import finalmission.room.dto.request.RoomRequest;
import io.restassured.RestAssured;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationAcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        TestHelper.signUpAsAdmin();
    }

    @Test
    @DisplayName("예약을 생성한다. (어드민 권한)")
    void createReservation() {
        // given
        String token = TestHelper.loginAsAdmin();

        TestHelper.postWithToken("/rooms", new RoomRequest("회의실"), token);

        var memberId = 1L;
        var roomId = 1L;
        var date = LocalDate.now();
        var startTime = LocalTime.of(10, 0);
        var endTime = LocalTime.of(11, 0);
        var purpose = "회의";
        var request = new ReservationCreateRequest(memberId, roomId, date, startTime, endTime, purpose);

        // when & then
        TestHelper.postWithToken("/reservations", request, token)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("memberId", is((int) memberId))
                .body("roomId", is((int) roomId))
                .body("date", is(date.toString()))
                .body("startTime", is(startTime.toString()))
                .body("endTime", is(endTime.toString()))
                .body("purpose", is(purpose));
    }

    @Test
    @DisplayName("모든 예약을 조회한다.")
    void findAllReservations() {
        // given
        String token = TestHelper.loginAsAdmin();

        TestHelper.postWithToken("/rooms", new RoomRequest("회의실"), token);
        TestHelper.postWithToken(
                "/reservations",
                new ReservationCreateRequest(
                        1L,
                        1L,
                        LocalDate.now(),
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0),
                        "회의"),
                token
        );

        // when & then
        TestHelper.getWithToken("/reservations", token)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(1));
    }

    @Test
    @DisplayName("나의 모든 예약을 조회한다.")
    void findAllMyReservations() {
        // given
        String token = TestHelper.loginAsAdmin();

        TestHelper.postWithToken("/rooms", new RoomRequest("회의실"), token);
        TestHelper.postWithToken(
                "/reservations",
                new ReservationCreateRequest(
                        1L,
                        1L,
                        LocalDate.now(),
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0),
                        "회의"),
                token
        );

        // when & then
        TestHelper.getWithToken("/reservations/mine", token)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(1));
    }

    @Test
    @DisplayName("예약을 수정한다.")
    void updateReservation() {
        // given
        String token = TestHelper.loginAsAdmin();

        TestHelper.postWithToken("/rooms", new RoomRequest("회의실"), token);
        long id = TestHelper.postWithToken(
                        "/reservations",
                        new ReservationCreateRequest(
                                1L,
                                1L,
                                LocalDate.now(),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                "회의"),
                        token
                ).then()
                .extract()
                .response()
                .jsonPath()
                .getLong("id");

        String purpose = "변경한 목적";
        var request = new ReservationUpdateRequest(purpose);

        // when
        TestHelper.patchWithToken("/reservations/" + id, request, token)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // then
        TestHelper.getWithToken("/reservations/" + id, token)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("purpose", is(purpose));
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void deleteReservation() {
        // given
        String token = TestHelper.loginAsAdmin();

        TestHelper.postWithToken("/rooms", new RoomRequest("회의실"), token);
        long id = TestHelper.postWithToken(
                        "/reservations",
                        new ReservationCreateRequest(
                                1L,
                                1L,
                                LocalDate.now(),
                                LocalTime.of(10, 0),
                                LocalTime.of(11, 0),
                                "회의"),
                        token
                ).then()
                .extract()
                .response()
                .jsonPath()
                .getLong("id");

        // when
        TestHelper.deleteWithToken("/reservations/" + id, token)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // then
        TestHelper.getWithToken("/reservations", token)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(0));
    }
}
