package finalmission.reservation.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.auth.dto.LoginMember;
import finalmission.reservation.dto.ReservationRequest;
import io.restassured.RestAssured;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class ReservationServiceTest {

    @LocalServerPort
    int port;
    @Autowired
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("이미 예약된 날짜, 사이트인 경우 예외가 발생한다.")
    @Test
    void duplicateTest() {
        LoginMember loginMember = new LoginMember(1L, "haru@woowa.com");
        ReservationRequest request = new ReservationRequest(LocalDate.of(2025, 8, 14), LocalDate.of(2025, 8, 18), 1L);
        assertThatThrownBy(() -> reservationService.create(loginMember, request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("예약되지 않은 날짜인 경우 예외가 발생하지 않는다.")
    @Test
    void notDuplicateTest() {
        LoginMember loginMember = new LoginMember(1L, "haru@woowa.com");
        ReservationRequest request = new ReservationRequest(LocalDate.of(2025, 8, 18), LocalDate.of(2025, 8, 21), 1L);
        assertThatCode(() -> reservationService.create(loginMember, request))
                .doesNotThrowAnyException();
    }
}