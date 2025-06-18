package finalmission.reservation.ui;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;

import finalmission.auth.jwt.JwtTokenProvider;
import finalmission.common.config.WebConfig;
import finalmission.member.domain.Member;
import finalmission.member.domain.Role;
import finalmission.reservation.domain.MailClient;
import finalmission.reservation.domain.Reservation;
import finalmission.reservation.domain.ReservationState;
import finalmission.reservation.intrastructure.client.exception.MailException;
import finalmission.reservation.intrastructure.repository.JpaReservationRepository;
import finalmission.reservation.ui.dto.ReservationUpdateRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import(WebConfig.class)
class CoachReservationControllerTest {

    @Autowired
    private JpaReservationRepository reservationRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @MockitoBean
    private MailClient mailClient;

    @Test
    @DisplayName("예약 수정")
    void test_update() {
        long id = 1L;
        LocalTime givenTime = LocalTime.MIN;
        ReservationUpdateRequest given
                = new ReservationUpdateRequest(id, LocalDate.now().plusDays(1), givenTime, 1L);
        Reservation past = reservationRepository.findById(id).get();

        Member member = new Member(id, "브라운", "brown@gmail.com", Role.COACH);
        String token = jwtTokenProvider.createToken(member);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookies("token", token)
                .body(given)
                .when().put(CoachReservationController.BASE_PATH + "/reservations")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        Reservation now = reservationRepository.findById(id).get();

        Assertions.assertThat(now.getTime()).isEqualTo(givenTime);
        Assertions.assertThat(now.getTime()).isNotEqualTo(past.getTime());

    }

    @Test
    @DisplayName("예약 삭제")
    void test_cancel() {
        long id = 1L;
        Reservation past = reservationRepository.findById(id).get();
        Assertions.assertThat(past.getState()).isEqualTo(ReservationState.WAITING);

        Member member = new Member(id, "브라운", "brown@gmail.com", Role.COACH);
        String token = jwtTokenProvider.createToken(member);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookies("token", token)
                .when().delete(CoachReservationController.BASE_PATH + "/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        Reservation now = reservationRepository.findById(id).get();
        Assertions.assertThat(now.getState()).isEqualTo(ReservationState.CANCEL);
    }

    @Test
    @DisplayName("코치의 모든 예약 조회")
    void test_getAllByCoach() {
        long id = 1L;
        int expectedSize = 2;
        List<Reservation> given = reservationRepository.findAllByCoach_Id(id);
        Assertions.assertThat(given.size()).isEqualTo(expectedSize);

        Member member = new Member(id, "브라운", "brown@gmail.com", Role.COACH);
        String token = jwtTokenProvider.createToken(member);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookies("token", token)
                .when().get(CoachReservationController.BASE_PATH + "/reservations")
                .then().log().all()
                .body("size()", is(expectedSize))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("예약 승인")
    void test_approval() {
        long id = 1L;
        Optional<Reservation> given = reservationRepository.findById(id);
        Assertions.assertThat(given.get().getState()).isEqualTo(ReservationState.WAITING);

        Member member = new Member(id, "브라운", "brown@gmail.com", Role.COACH);
        String token = jwtTokenProvider.createToken(member);

        willDoNothing().given(mailClient).send(any());
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookies("token", token)
                .when().patch(CoachReservationController.BASE_PATH + "/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        Optional<Reservation> actual = reservationRepository.findById(id);
        Assertions.assertThat(actual.get().getState()).isEqualTo(ReservationState.APPROVAL);
    }

    @Test
    @DisplayName("예약 승인-메일 실패")
    void test_approval_mailFail() {
        long id = 1L;
        Optional<Reservation> given = reservationRepository.findById(id);
        Assertions.assertThat(given.get().getState()).isEqualTo(ReservationState.WAITING);

        Member member = new Member(id, "브라운", "brown@gmail.com", Role.COACH);
        String token = jwtTokenProvider.createToken(member);

        willThrow(new MailException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "에러"))
                .given(mailClient)
                .send(any());

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookies("token", token)
                .when().patch(CoachReservationController.BASE_PATH + "/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        Optional<Reservation> actual = reservationRepository.findById(id);
        Assertions.assertThat(actual.get().getState()).isEqualTo(ReservationState.WAITING);
    }
}
