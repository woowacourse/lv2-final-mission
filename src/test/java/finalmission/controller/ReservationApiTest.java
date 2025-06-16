package finalmission.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

import finalmission.domain.Member;
import finalmission.domain.Reservation;
import finalmission.domain.Role;
import finalmission.domain.Seat;
import finalmission.dto.layer.AccessTokenContent;
import finalmission.dto.request.AddReservationRequest;
import finalmission.dto.request.UpdateReservationByIdRequest;
import finalmission.repository.MemberRepository;
import finalmission.repository.ReservationRepository;
import finalmission.repository.SeatRepository;
import finalmission.utility.JwtProvider;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.RestDocumentationContextProvider;

@AutoConfigureRestDocs
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReservationApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private RestDocumentationContextProvider restDocumentation;

    @BeforeEach
    void setup() {
        RestAssured.filters(documentationConfiguration(restDocumentation));
    }

    @AfterEach
    void afterEach() {
        RestAssured.reset();
        reservationRepository.deleteAll();
        memberRepository.deleteAll();
        seatRepository.deleteAll();
    }

    @Nested
    @DisplayName("자리 마다의 예약 현황을 조회할 수 있다.")
    public class FindAllReservationBySeat {

        @DisplayName("정상적으로 자리 마다의 예약 현환을 조회할 수 있다.")
        @Test
        void findAllReservationBySeat() {
            // given
            Member member = memberRepository.save(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = seatRepository.save(new Seat("ABC"));
            List<Reservation> reservations = List.of(
                    reservationRepository.save(new Reservation(member, seat, "공부1", LocalDate.now().plusDays(1))),
                    reservationRepository.save(new Reservation(member, seat, "공부2", LocalDate.now().plusDays(2))),
                    reservationRepository.save(new Reservation(member, seat, "공부3", LocalDate.now().plusDays(3))));

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .queryParam("seatId", seat.getId())
                    .filter(document("find_reservation_by_seat"))
                    .when().log().all()
                    .get("/reservation")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", is(reservations.size()));
        }
    }

    @Nested
    @DisplayName("회원의 모든 자리를 조회할 수 있다.")
    public class FindAllReservationByMember {

        @DisplayName("정상적으로 회원의 모든 자리를 조회할 수 있다.")
        @Test
        void findAllReservationByMember() {
            // given
            Member member = memberRepository.save(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = seatRepository.save(new Seat("ABC"));
            List<Reservation> reservations = List.of(
                    reservationRepository.save(new Reservation(member, seat, "공부1", LocalDate.now().plusDays(1))),
                    reservationRepository.save(new Reservation(member, seat, "공부2", LocalDate.now().plusDays(2))),
                    reservationRepository.save(new Reservation(member, seat, "공부3", LocalDate.now().plusDays(3))));

            String accessToken = jwtProvider.makeAccessToken(new AccessTokenContent(member.getId(), member.getRole()));

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .header("Cookie", "access=" + accessToken)
                    .filter(document("find_reservation_by_member"))
                    .when().log().all()
                    .get("/member/reservation")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", is(reservations.size()));
        }

        @DisplayName("인증되지 않은 사용자의 경우 조회가 불가능하다.")
        @Test
        void cannotFindAllReservationByUnAuthorizedMember() {
            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .filter(document("find_reservation_by_member/unauthorized"))
                    .when().log().all()
                    .get("/member/reservation")
                    .then()
                    .statusCode(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @Nested
    @DisplayName("자신의 예약의 세부사항을 조회할 수 있다.")
    public class FindReservationById {

        @DisplayName("정상적으로 자신의 예약의 세부사항을 조회할 수 있다.")
        @Test
        void findReservationById() {
            // given
            Member member = memberRepository.save(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = seatRepository.save(new Seat("ABC"));
            Reservation reservation = reservationRepository.save(
                    new Reservation(member, seat, "공부1", LocalDate.now().plusDays(1)));

            String accessToken = jwtProvider.makeAccessToken(new AccessTokenContent(member.getId(), member.getRole()));

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .pathParam("reservationId", reservation.getId())
                    .header("Cookie", "access=" + accessToken)
                    .filter(document("find_reservation_by_id"))
                    .when().log().all()
                    .get("/reservation/{reservationId}")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", notNullValue());
        }

        @DisplayName("다른 사람의 예약의 세부 사항을 조회할 수 없다.")
        @Test
        void cannotFindOtherMemberReservation() {
            // given
            Member member = memberRepository.save(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            Member otherMember = memberRepository.save(new Member("test1@test.com", "qwer1234!", "park", Role.GENERAL));
            Seat seat = seatRepository.save(new Seat("ABC"));
            Reservation reservation = reservationRepository.save(
                    new Reservation(otherMember, seat, "공부1", LocalDate.now().plusDays(1)));

            String accessToken = jwtProvider.makeAccessToken(new AccessTokenContent(member.getId(), member.getRole()));

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .pathParam("reservationId", reservation.getId())
                    .header("Cookie", "access=" + accessToken)
                    .filter(document("find_reservation_by_id/other_member"))
                    .when().log().all()
                    .get("/reservation/{reservationId}")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    @DisplayName("예약을 추가할 수 있다.")
    public class AddReservation {

        @DisplayName("정상적으로 예약을 추가할 수 있다.")
        @Test
        void canAddReservation() {
            // given
            Member member = memberRepository.save(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = seatRepository.save(new Seat("ABC"));

            String accessToken = jwtProvider.makeAccessToken(new AccessTokenContent(member.getId(), member.getRole()));
            AddReservationRequest request = new AddReservationRequest(seat.getId(), "공부", LocalDate.now().plusDays(1));

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .header("Cookie", "access=" + accessToken)
                    .body(request)
                    .filter(document("add_reservation"))
                    .when().log().all()
                    .post("/reservation")
                    .then()
                    .statusCode(HttpStatus.CREATED.value());
        }

        @DisplayName("이미 예약이 완료된 경우에는 예약을 추가할 수 없다.")
        @Test
        void cannotAddAlreadyExistedReservation() {
            // given
            Member otherMember = memberRepository.save(new Member("test1@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = seatRepository.save(new Seat("ABC"));
            Reservation reservation = reservationRepository.save(
                    new Reservation(otherMember, seat, "공부1", LocalDate.now().plusDays(1)));

            Member member = memberRepository.save(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));

            String accessToken = jwtProvider.makeAccessToken(new AccessTokenContent(member.getId(), member.getRole()));
            AddReservationRequest request = new AddReservationRequest(seat.getId(), "공부", LocalDate.now().plusDays(1));

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .header("Cookie", "access=" + accessToken)
                    .filter(document("add_reservation/already_reserved"))
                    .body(request)
                    .when().log().all()
                    .post("/reservation")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }

        @DisplayName("과거의 날짜로는 예약이 불가능하다.")
        @Test
        void cannotAddPastDateReservation() {
            // given
            Member member = memberRepository.save(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = seatRepository.save(new Seat("ABC"));

            String accessToken = jwtProvider.makeAccessToken(new AccessTokenContent(member.getId(), member.getRole()));
            AddReservationRequest request = new AddReservationRequest(seat.getId(), "공부", LocalDate.now().minusDays(1));

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .header("Cookie", "access=" + accessToken)
                    .body(request)
                    .filter(document("add_reservation/past_date"))
                    .when().log().all()
                    .post("/reservation")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    @DisplayName("예약의 부가적인 내용을 수정할 수 있다.")
    public class UpdateReservationById {

        @DisplayName("정상적으로 예약의 부가적인 내용을 수정할 수 있다.")
        @Test
        void canUpdateReservationById() {
            // given
            Member member = memberRepository.save(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = seatRepository.save(new Seat("ABC"));
            Reservation reservation = reservationRepository.save(
                    new Reservation(member, seat, "공부1", LocalDate.now().plusDays(1)));

            String accessToken = jwtProvider.makeAccessToken(new AccessTokenContent(member.getId(), member.getRole()));
            UpdateReservationByIdRequest request = new UpdateReservationByIdRequest(reservation.getId(), "수정된 사유");

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .header("Cookie", "access=" + accessToken)
                    .body(request)
                    .filter(document("update_reservation"))
                    .when().log().all()
                    .put("/reservation")
                    .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @DisplayName("다른 회원의 예약을 수정할 수 없다.")
        @Test
        void cannotUpdateOtherMemberReservation() {
            // given
            Member otherMember = memberRepository.save(new Member("test1@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = seatRepository.save(new Seat("ABC"));
            Reservation reservation = reservationRepository.save(
                    new Reservation(otherMember, seat, "공부1", LocalDate.now().plusDays(1)));

            Member member = memberRepository.save(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));

            String accessToken = jwtProvider.makeAccessToken(new AccessTokenContent(member.getId(), member.getRole()));
            UpdateReservationByIdRequest request = new UpdateReservationByIdRequest(reservation.getId(), "수정된 사유");

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .header("Cookie", "access=" + accessToken)
                    .filter(document("update_reservation/other_member"))
                    .body(request)
                    .when().log().all()
                    .put("/reservation")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    @DisplayName("예약을 삭제할 수 있다.")
    public class DeleteReservationById {

        @DisplayName("정상적으로 예약을 삭제할 수 있다.")
        @Test
        void canDeleteReservationById() {
            // given
            Member member = memberRepository.save(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = seatRepository.save(new Seat("ABC"));
            Reservation reservation = reservationRepository.save(
                    new Reservation(member, seat, "공부1", LocalDate.now().plusDays(1)));

            String accessToken = jwtProvider.makeAccessToken(new AccessTokenContent(member.getId(), member.getRole()));

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .header("Cookie", "access=" + accessToken)
                    .pathParam("reservationId", reservation.getId())
                    .filter(document("delete_reservation"))
                    .when().log().all()
                    .delete("/reservation/{reservationId}")
                    .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @DisplayName("다른 회원의 예약을 삭제할 수 없다.")
        @Test
        void cannotUpdateOtherMemberReservation() {
            // given
            Member otherMember = memberRepository.save(new Member("test1@test.com", "qwer1234!", "kim", Role.GENERAL));
            Seat seat = seatRepository.save(new Seat("ABC"));
            Reservation reservation = reservationRepository.save(
                    new Reservation(otherMember, seat, "공부1", LocalDate.now().plusDays(1)));

            Member member = memberRepository.save(new Member("test@test.com", "qwer1234!", "kim", Role.GENERAL));

            String accessToken = jwtProvider.makeAccessToken(new AccessTokenContent(member.getId(), member.getRole()));

            // when & then
            RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .port(port)
                    .header("Cookie", "access=" + accessToken)
                    .pathParam("reservationId", reservation.getId())
                    .filter(document("delete_reservation/other_member"))
                    .when().log().all()
                    .delete("/reservation/{reservationId}")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }
}
