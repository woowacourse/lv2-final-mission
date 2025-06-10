package finalmission;

import finalmission.dto.ReservationResponseDto;
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
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ReservationAcceptanceTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    SeatRepository seatRepository;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
        seatRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("예약을 저장한다")
    void test1() {
        // given
        Member member = saveMember();
        Seat seat = saveSeat();

        Map<String, Object> params = new HashMap<>();
        params.put("seatId", seat.getId());
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

    @Test
    @DisplayName("나의 예약을 모두 조회한다")
    void test2() {
        // given
        Member member = saveMember();
        Seat seat = saveSeat();
        saveReservation(member, seat, LocalDate.now().plusDays(1));
        saveReservation(member, seat, LocalDate.now().plusDays(2));
        saveReservation(member, seat, LocalDate.now().plusDays(3));

        // when
        List<ReservationResponseDto> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", jwtTokenProvider.createToken(String.valueOf(member.getId())))
                .when().get("/reservation/mine")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", ReservationResponseDto.class);

        // then
        assertThat(response).hasSize(3);
    }

    private Reservation saveReservation(Member member, Seat seat, LocalDate date) {
        return reservationRepository.save(
                new Reservation(
                        member,
                        seat,
                        date,
                        LocalTime.of(12, 30),
                        LocalTime.of(13, 30)
                )
        );
    }

    private Seat saveSeat() {
        return seatRepository.save(new Seat("백스윙", 1));
    }

    private Member saveMember() {
        return memberRepository.save(new Member(
                "히로",
                "example@gmail.com",
                "password"
        ));
    }
}
