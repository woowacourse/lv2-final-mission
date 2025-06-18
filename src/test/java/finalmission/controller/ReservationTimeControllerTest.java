package finalmission.controller;

import finalmission.domain.MemberRole;
import finalmission.dto.request.CreateReservationTimeRequest;
import finalmission.dto.request.CreateTokenRequest;
import finalmission.entity.Member;
import finalmission.jwt.JwtTokenProvider;
import finalmission.repository.MemberRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalTime;
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
class ReservationTimeControllerTest {

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
    @DisplayName("전체 예약 가능 시간을 조회한다.")
    void getReservationTimes() {
        //given //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    @DisplayName("예약 가능 시간을 추가한다.")
    void addReservationTime() {
        //given
        CreateReservationTimeRequest request = new CreateReservationTimeRequest(LocalTime.of(12, 34));

        //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)
                .when().post("/times")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("같은 예약 가능 시간은 추가할 수 없다.")
    void cannotAddSameReservationTime() {
        //given
        CreateReservationTimeRequest request = new CreateReservationTimeRequest(LocalTime.of(10, 0));

        //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)
                .when().post("/times")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("예약 가능 시간을 삭제한다.")
    void deleteReservationTime() {
        //given //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .pathParam("id", 2)
                .when().delete("times/{id}")
                .then().log().all()
                .statusCode(204);
    }

    @Test
    @DisplayName("사용 중인 예약 가능 시간은 삭제할 수 없다.")
    void cannotDeleteUsingReservationTime() {
        //given //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .pathParam("id", 1)
                .when().delete("times/{id}")
                .then().log().all()
                .statusCode(409);
    }

    @Test
    @DisplayName("존재하지 않는 예약 가능 시간은 삭제할 수 없다.")
    void cannotDeleteNotExistedReservationTime() {
        //given //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .pathParam("id", 10000)
                .when().delete("times/{id}")
                .then().log().all()
                .statusCode(404);
    }
}
