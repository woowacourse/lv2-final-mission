package finalmission.controller;

import finalmission.domain.MemberRole;
import finalmission.dto.request.CreateReservationRequest;
import finalmission.dto.request.CreateTokenRequest;
import finalmission.entity.Member;
import finalmission.jwt.JwtTokenProvider;
import finalmission.repository.MemberRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = {"/test-data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    String token;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        Member member = memberRepository.save(new Member("어드민", "test_admin@test.com", "test", MemberRole.ADMIN));
        CreateTokenRequest request = new CreateTokenRequest(member, new Date());
        token = jwtTokenProvider.createTokenByMember(request);

    }

    @Test
    @DisplayName("모든 예약 정보를 조회한다.")
    void getAllReservation() {
        //given //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @DisplayName("사용자의 모든 예약 정보를 조회한다.")
    void getAllMyReservation() {
        //given
        Member member = memberRepository.findById(1L)
                .orElseThrow(IllegalArgumentException::new);
        String memberToken = jwtTokenProvider.createTokenByMember(new CreateTokenRequest(member, new Date()));

        // when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", memberToken)
                .when().get("/reservations/mine")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void addReservation() {
        //given
        CreateReservationRequest request = new CreateReservationRequest(1L, LocalDate.of(4000, 1, 3), 1L);

        //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("동일한 예약은 추가할 수 없다.")
    void cannotAddSameReservation() {
        //given
        CreateReservationRequest request = new CreateReservationRequest(1L, LocalDate.of(3000, 1, 1), 1L);

        //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("블랙 리스트에 존재하는 사용자는 예약을 추가할 수 없다.")
    void cannotAddReservationWhenBannedMember() {
        //given
        Member banned = memberRepository.findById(2L)
                .orElseThrow(IllegalArgumentException::new);
        String bannedToken = jwtTokenProvider.createTokenByMember(new CreateTokenRequest(banned, new Date()));

        CreateReservationRequest request = new CreateReservationRequest(1L, LocalDate.of(4000, 1, 3), 1L);

        //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", bannedToken)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(403);
    }

    @Test
    @DisplayName("존재하지 않는 예약 가능 시간인 예약은 추가할 수 없다.")
    void cannotAddReservationNotExistedTime() {
        //given
        CreateReservationRequest request = new CreateReservationRequest(1L, LocalDate.of(4000, 1, 3), 1000L);

        //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(404);
    }

    @Test
    @DisplayName("존재하지 않는 회의실인 예약은 추가할 수 없다.")
    void cannotAddReservationNotExistedMeetingRoom() {
        //given
        CreateReservationRequest request = new CreateReservationRequest(1000L, LocalDate.of(4000, 1, 3), 1L);

        //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(404);
    }

    @Test
    @DisplayName("과거 시간인 예약은 추가할 수 없다.")
    void cannotAddReservationPastDate() {
        //given
        CreateReservationRequest request = new CreateReservationRequest(1L, LocalDate.of(1000, 1, 3), 1L);

        //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void deleteReservation() {
        //given
        CreateReservationRequest request = new CreateReservationRequest(1L, LocalDate.of(4000, 1, 3), 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)
                .when().post("/reservations")
                .then().log().all();

        // when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .pathParam("id", 2)
                .when().delete("/reservations/{id}")
                .then().log().all()
                .statusCode(204);
    }

    @Test
    @DisplayName("예약자가 아닌 예약은 삭제할 수 없다.")
    void cannotDeleteOtherMemberReservation() {
        //given

        // when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .pathParam("id", 1)
                .when().delete("/reservations/{id}")
                .then().log().all()
                .statusCode(409);
    }

    @Test
    @DisplayName("존재하지 않는 예약은 삭제할 수 없다.")
    void cannotDeleteNotExistedReservation() {
        //given

        // when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .pathParam("id", 10000)
                .when().delete("/reservations/{id}")
                .then().log().all()
                .statusCode(404);
    }
}
