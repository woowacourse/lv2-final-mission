package finalmission.member.controller;

import finalmission.member.dto.SigninRequest;
import finalmission.member.dto.SignupRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MemberControllerTest {

    @Test
    @DisplayName("회원가입을 할 수 있다")
    public void test1() {
        RestAssured.given().log().all()
                .body(new SignupRequest("Solar", "4@a.com", "wooteco7"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/signup")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("가입한 계정 정보로 로그인할 수 있다")
    public void test2() {
        RestAssured.given().log().all()
                .body(new SigninRequest("4@a.com", "wooteco7"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all()
                .statusCode(500);

        RestAssured.given().log().all()
                .body(new SignupRequest("Solar", "4@a.com", "wooteco7"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/signup")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .body(new SigninRequest("4@a.com", "wooteco7"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login")
                .then().log().all()
                .statusCode(200);
    }
}
