package finalmission;

import static finalmission.TestFixture.login;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoginTest {

    @Test
    @DisplayName("로그인이 잘 동작한다")
    void loginWorks() {
        Assertions.assertDoesNotThrow(() -> login());
    }

    @Test
    @DisplayName("로그인된 사용자 정보를 가져올 수 있다")
    void loginCheck() {
        String token = login();

        RestAssured.given()
            .cookies("token", token)
            .when().get("/login/check")
            .then()
            .statusCode(200)
            .body("email", is("123"));
    }
}
