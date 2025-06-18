package finalmission.api;

import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MemberApiTest {

    @Test
    void 회원가입_성공() {
        //given
        Map<String, Object> signUpRequest = new HashMap<>();
        signUpRequest.put("email", "river@naver.com");
        signUpRequest.put("password", "river123");

        //when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest).post("/api/members")
                .then().log().all()
                .statusCode(201)
                .body("email", equalTo("river@naver.com"));
    }
}
