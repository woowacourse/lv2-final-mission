package finalmission.controller;

import finalmission.auth.controller.dto.request.LoginRequest;
import finalmission.member.controller.dto.request.MemberRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthControllerTest {

    private MemberRequest memberRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    public void setUp() {
        memberRequest = new MemberRequest(
                "ywcsuwon@naver.com",
                "123123",
                "Lemon"
        );

        loginRequest = new LoginRequest("123123", "ywcsuwon@naver.com");
    }

    @Test
    public void test() {
        RestAssured.given().log().all()
                .contentType("application/json")
                .body(memberRequest)
                .when().post("/member")
                .then()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given().log().all()
                .contentType("application/json")
                .body(loginRequest)
                .when().post("/login")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
