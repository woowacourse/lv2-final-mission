package finalmission.api;

import static org.hamcrest.Matchers.notNullValue;

import finalmission.domain.Member;
import finalmission.infrastructure.MemberRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthApiTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 로그인_성공() {
        //given
        memberRepository.save(new Member(null, "이름", "email@email.com", "password"));
        Map<String, Object> loginRequest = new HashMap<>();
        loginRequest.put("email", "email@email.com");
        loginRequest.put("password", "password");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when().post("api/auth/login")
                .then()
                .statusCode(200)
                .cookie("token", notNullValue());
    }

    @Test
    void 로그인_실패_잘못된_이메일() {
        //given
        memberRepository.save(new Member(null, "이름", "email@email.com", "password"));
        Map<String, Object> loginRequest = new HashMap<>();
        loginRequest.put("email", "email@emaill.com");
        loginRequest.put("password", "password");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when().post("api/auth/login")
                .then()
                .statusCode(401);
    }

    @Test
    void 로그인_실패_잘못된_비밀번호() {
        //given
        memberRepository.save(new Member(null, "이름", "email@email.com", "password"));
        Map<String, Object> loginRequest = new HashMap<>();
        loginRequest.put("email", "email@email.com");
        loginRequest.put("password", "passwordd");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when().post("api/auth/login")
                .then()
                .statusCode(401);
    }
}
