package finalmission.controller;

import finalmission.domain.MemberRole;
import finalmission.dto.request.CreateBlackListRequest;
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
class BlackListControllerTest {

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
    @DisplayName("전체 블랙 리스트 정보를 조회한다.")
    void getAllBlackList() {
        //given //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .when().get("/blacklists")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @DisplayName("블랙 리스트를 추가한다.")
    void addBlackList() {
        //given
        CreateBlackListRequest request = new CreateBlackListRequest(3L, "이유");

        //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)
                .when().post("/blacklists")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("이미 존재하는 유저를 블랙 리스트에 추가할 수 없다.")
    void cannotAddBlackList() {
        //given
        CreateBlackListRequest request = new CreateBlackListRequest(2L, "이유");

        //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)
                .when().post("/blacklists")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("블랙 리스트를 제거한다.")
    void deleteBlackList() {
        // given //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .pathParam("id", "1")
                .when().delete("/blacklists/{id}")
                .then().log().all()
                .statusCode(204);
    }

    @Test
    @DisplayName("존재하지 않는 블랙 리스트는 제거할 수 없다.")
    void cannotDeleteNotExistedBlackList() {
        // given //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .pathParam("id", "1000")
                .when().delete("/blacklists/{id}")
                .then().log().all()
                .statusCode(404);
    }
}
