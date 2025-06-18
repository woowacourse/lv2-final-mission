package finalmission.api;

import static org.hamcrest.Matchers.equalTo;

import finalmission.domain.Guest;
import finalmission.domain.Member;
import finalmission.domain.Price;
import finalmission.domain.Reservation;
import finalmission.domain.ReservationDateTime;
import finalmission.dto.response.ReservationResponse;
import finalmission.infrastructure.MemberRepository;
import finalmission.infrastructure.ReservationDateTimeRepository;
import finalmission.infrastructure.ReservationRepository;
import finalmission.infrastructure.jwt.JwtTokenProvider;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationApiTest {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReservationDateTimeRepository reservationDateTimeRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private ReservationDateTime dateTime1;
    private ReservationDateTime dateTime2;
    private ReservationDateTime dateTime3;

    private Member member1;
    private Member member2;

    @BeforeEach
    void setUp() {
        dateTime1 = reservationDateTimeRepository.save(
                ReservationDateTime.createWithoutId(LocalDate.of(2025, 5, 5), LocalTime.of(10, 0)));
        dateTime2 = reservationDateTimeRepository.save(
                ReservationDateTime.createWithoutId(LocalDate.of(2025, 5, 6), LocalTime.of(10, 0)));
        dateTime3 = reservationDateTimeRepository.save(
                ReservationDateTime.createWithoutId(LocalDate.of(2025, 5, 7), LocalTime.of(10, 0)));

        member1 = memberRepository.save(new Member(null, "이름1", "email1@emial.com", "password1"));
        member2 = memberRepository.save(new Member(null, "이름2", "email2@emial.com", "password2"));
        memberRepository.save(new Member(null, "이름3", "email3@emial.com", "password3"));
    }

    @Test
    void 사용자가_예약을_조회한다() {
        //given
        String token = jwtTokenProvider.createToken(member1.getId().toString());

        reservationRepository.save(Reservation.createWithoutId(dateTime1, member1, new Guest(10), Price.WEEKDAY));
        reservationRepository.save(Reservation.createWithoutId(dateTime2, member1, new Guest(10), Price.WEEKDAY));
        reservationRepository.save(Reservation.createWithoutId(dateTime3, member1, new Guest(10), Price.WEEKDAY));

        //when & then
        List<ReservationResponse> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .when().get("/api/reservations/my")
                .then().log().all()
                .statusCode(200)
                .extract().jsonPath().getList(".", ReservationResponse.class);

        SoftAssertions soft = new SoftAssertions();

        soft.assertThat(response).hasSize(3);
        soft.assertThat(response.getFirst().userName()).isEqualTo("이름1");
        soft.assertThat(response.getFirst().guest()).isEqualTo(10);
        soft.assertAll();
    }

    @Test
    void 사용자가_예약을_추가한다() {
        //given
        String token = jwtTokenProvider.createToken(member1.getId().toString());

        Map<String, Object> reservationRequest = new HashMap<>();
        reservationRequest.put("dateTimeId", "1");
        reservationRequest.put("guest", "10");

        //when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationRequest)
                .cookie("token", token)
                .when().post("/api/reservations")
                .then().log().all()
                .statusCode(201)
                .body("date", equalTo("2025-05-05"))
                .body("startAt", equalTo("10:00:00"))
                .body("guest", equalTo(10));
    }

    @Test
    void 사용자가_예약을_수정한다() {
        //given
        String token = jwtTokenProvider.createToken(member1.getId().toString());

        Reservation originalReservation = reservationRepository
                .save(Reservation.createWithoutId(dateTime1, member1, new Guest(10), Price.WEEKDAY));

        Map<String, Object> reservationUpdateRequest = new HashMap<>();
        reservationUpdateRequest.put("dateTimeId", "2");
        reservationUpdateRequest.put("guest", "11");

        //when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationUpdateRequest)
                .cookie("token", token)
                .when().patch("/api/reservations/{id}", originalReservation.getId())
                .then().log().all()
                .statusCode(201)
                .body("date", equalTo("2025-05-06"))
                .body("guest", equalTo(11));
    }

    @Test
    void 다른_사용자가_사용자가_예약을_수정한다() {
        //given
        String token = jwtTokenProvider.createToken(member1.getId().toString());

        Reservation originalReservation = reservationRepository
                .save(Reservation.createWithoutId(dateTime1, member2, new Guest(10), Price.WEEKDAY));

        Map<String, Object> reservationUpdateRequest = new HashMap<>();
        reservationUpdateRequest.put("dateTimeId", "2");
        reservationUpdateRequest.put("guest", "11");

        //when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationUpdateRequest)
                .cookie("token", token)
                .when().patch("/api/reservations/{id}", originalReservation.getId())
                .then().log().all()
                .statusCode(401);
    }

    @Test
    void 사용자가_예약을_삭제한다() {
        //given
        String token = jwtTokenProvider.createToken(member1.getId().toString());
        Reservation reservation = reservationRepository.save(
                Reservation.createWithoutId(dateTime1, member1, new Guest(10), Price.WEEKDAY));

        //when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .when().delete("/api/reservations/{id}", reservation.getId())
                .then().log().all()
                .statusCode(204);
    }

    @Test
    void 다른_사용자가_예약을_삭제한다() {
        //given
        String token = jwtTokenProvider.createToken(member1.getId().toString());
        Reservation reservation = reservationRepository.save(
                Reservation.createWithoutId(dateTime1, member2, new Guest(10), Price.WEEKDAY));

        //when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .when().delete("/api/reservations/{id}", reservation.getId())
                .then().log().all()
                .statusCode(401);
    }
}
