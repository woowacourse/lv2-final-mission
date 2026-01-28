package finalmission.member.controller;

import finalmission.member.controller.dto.request.LoginRequest;
import finalmission.member.controller.dto.request.SignUpRequest;
import finalmission.member.service.MemberService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    @Test
    void 회원가입_요청_API_테스트() {
        SignUpRequest request = new SignUpRequest("testUser", "1234");

        RestAssured.given().log().all()
                .contentType("application/json")
                .body(request)
                .when().post("/signup")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 이미_존재하는_멤버_회원가입_요청_테스트() {
        String nickname = "testUser";
        String password = "1234";

        SignUpRequest signUpRequest = new SignUpRequest(nickname, password);
        memberService.signup(signUpRequest);

        SignUpRequest request = new SignUpRequest("testUser", "1234");

        RestAssured.given().log().all()
                .contentType("application/json")
                .body(request)
                .when().post("/signup")
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value());
    }


    @Test
    void 로그인_요청_API_테스트() {
        String nickname = "testUser";
        String password = "1234";

        SignUpRequest signUpRequest = new SignUpRequest(nickname, password);
        memberService.signup(signUpRequest);

        LoginRequest loginRequest = new LoginRequest(nickname, password);

        RestAssured.given().log().all()
                .contentType("application/json")
                .body(loginRequest)
                .when().post("/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @ParameterizedTest
    @CsvSource(value = {"testUser, 하나둘셋넷", "testUser1,1234"})
    void 잘못된_로그인_정보_요청_테스트(String nickname, String password) {
        SignUpRequest signUpRequest = new SignUpRequest("testUser", "1234");
        memberService.signup(signUpRequest);

        LoginRequest loginRequest = new LoginRequest(nickname, password);

        RestAssured.given().log().all()
                .contentType("application/json")
                .body(loginRequest)
                .when().post("/login")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
