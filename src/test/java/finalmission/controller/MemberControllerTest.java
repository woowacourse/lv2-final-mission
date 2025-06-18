package finalmission.controller;

import finalmission.domain.MemberRole;
import finalmission.dto.request.CreateTokenRequest;
import finalmission.dto.request.LoginRequest;
import finalmission.dto.request.SignUpRequest;
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

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = {"/test-data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class MemberControllerTest {

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
    @DisplayName("이메일과 패스워드를 이용하여 로그인한다.")
    void login() {
        //given
        LoginRequest request = new LoginRequest("flint@flint.com", "flint");

        // when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().get("/login")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("맞지 않는 이메일과 패스워드는 로그인할 수 없다.")
    void cannotLogin() {
        //given
        LoginRequest request = new LoginRequest("flint@flint.com", "test");

        // when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().get("/login")
                .then().log().all()
                .statusCode(401);
    }

    @Test
    @DisplayName("로그아웃한다.")
    void logout() {
        //given //when //then
        RestAssured.given().log().all()
                .cookie("token", token)
                .when().get("/logout")
                .then().log().all()
                .statusCode(200)
                .cookie("token", "");
    }

    @Test
    @DisplayName("이메일, 패스워드, 이름을 기반으로 회원 가입한다.")
    void signup() {
        //given
        SignUpRequest request = new SignUpRequest("new@new.com", "new", "new");

        //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/signup")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("이미 사용 중인 이메일으로 회원 가입할 수 없다.")
    void cannotSignupAlreadyUsingEmail() {
        //given
        SignUpRequest request = new SignUpRequest("flint@flint.com", "new", "new");

        //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/signup")
                .then().log().all()
                .statusCode(400);
    }
}
