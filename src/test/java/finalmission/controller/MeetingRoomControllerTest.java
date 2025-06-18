package finalmission.controller;

import finalmission.domain.MemberRole;
import finalmission.dto.request.CreateMeetingRoomRequest;
import finalmission.dto.request.CreateTokenRequest;
import finalmission.entity.Member;
import finalmission.jwt.JwtTokenProvider;
import finalmission.repository.MemberRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
class MeetingRoomControllerTest {

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
    @DisplayName("존재하는 모든 회의실 정보를 조회한다.")
    void getAllMeetingRoom() {
        //given //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .when().get("/meeting-rooms")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    @DisplayName("회의실 정보를 추가한다.")
    void addMeetingRoom() {
        //given
        CreateMeetingRoomRequest request = new CreateMeetingRoomRequest("추가", "추가", 100);

        //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)
                .when().post("/meeting-rooms")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("같은 회의실 명인 회의실 정보는 추가할 수 없다.")
    void cannotAddSameMeetingRoom() {
        //given
        CreateMeetingRoomRequest request = new CreateMeetingRoomRequest("test1", "test1", 100);

        //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)
                .when().post("/meeting-rooms")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("양수가 아닌 가용 인원인 회의실 정보는 추가할 수 없다.")
    void cannotAddNotValidAvailableCount() {
        //given
        CreateMeetingRoomRequest request = new CreateMeetingRoomRequest("test1", "test1", 0);

        //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)
                .when().post("/meeting-rooms")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("회의실 정보를 삭제한다.")
    void deleteMeetingRoom() {
        //given //when //then
        RestAssured.given().log().all()
                .cookie("token", token)
                .pathParam("id", 2)
                .when().delete("/meeting-rooms/{id}")
                .then().log().all()
                .statusCode(204);
    }

    @Test
    @DisplayName("예약 중인 회의실 정보는 삭제할 수 없다.")
    void cannotDeleteUsingMeetingRoom() {
        //given //when //then
        RestAssured.given().log().all()
                .cookie("token", token)
                .pathParam("id", 1)
                .when().delete("/meeting-rooms/{id}")
                .then().log().all()
                .statusCode(409);
    }

    @Test
    @DisplayName("존재하지 않는 회의실 정보는 삭제할 수 없다.")
    void cannotDeleteNotExistMeetingRoom() {
        //given //when //then
        RestAssured.given().log().all()
                .cookie("token", token)
                .pathParam("id", 10000)
                .when().delete("/meeting-rooms/{id}")
                .then().log().all()
                .statusCode(404);
    }
}
