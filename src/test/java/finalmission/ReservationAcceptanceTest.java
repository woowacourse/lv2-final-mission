package finalmission;

import finalmission.model.Member;
import finalmission.model.Reservation;
import finalmission.model.Seat;
import finalmission.repository.MemberRepository;
import finalmission.repository.ReservationRepository;
import finalmission.repository.SeatRepository;
import finalmission.support.JwtTokenProvider;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ReservationAcceptanceTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    SeatRepository seatRepository;

    @Test
    @DisplayName("예약을 저장한다")
    void test1() {
        // given
        Member member = memberRepository.save(new Member(
                "히로",
                "example@gmail.com",
                "password"
        ));
        Seat seat = seatRepository.save(new Seat("백스윙", 1));

        Map<String, Object> params = new HashMap<>();
        params.put("seatId", 1L);
        params.put("reservationDate", String.valueOf(LocalDate.now().plusDays(1)));
        params.put("startAt", String.valueOf(LocalTime.of(12, 30)));
        params.put("endAt", String.valueOf(LocalTime.of(13, 30)));

        // when
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", jwtTokenProvider.createToken(String.valueOf(member.getId())))
                .body(params)
                .when().post("/reservation")
                .then().log().all()
                .statusCode(200);

        // then
        List<Reservation> all = reservationRepository.findAll();
    }
}
