package finalmission.e2e;

import finalmission.member.dto.LoginRequest;
import finalmission.member.dto.SignupRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {
        "spring.sql.init.data-locations=classpath:test-data.sql"
})
public class MemberIntegrationTest {

    public static final String REGULAR_EMAIL = "regular@gmail.com";
    public static final String REGULAR_NAME = "regular";
    public static final String PASSWORD = "password";

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void signup() {
        RestAssured.given().log().all()
                .body(new SignupRequest(REGULAR_NAME, REGULAR_EMAIL, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/member/signup")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    void login() {
        signup();
        RestAssured.given().log().all()
                .body(new LoginRequest(REGULAR_EMAIL, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/member/login")
                .then().log().all()
                .statusCode(200);
    }
}
