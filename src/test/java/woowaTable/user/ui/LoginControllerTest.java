package woowaTable.user.ui;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import woowaTable.user.application.JwtHandler;
import woowaTable.user.application.TokenCookieService;
import woowaTable.user.application.dto.LoginRequest;

@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoginControllerTest {

    @LocalServerPort
    private int port;

    private final JwtHandler jwtHandler;

    @Autowired
    public LoginControllerTest(final JwtHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 로그인에_성공하면_JWT_accessToken을_받는다() {
        // given
        final String email = "mj043000@naver.com";
        final String password = "1234";

        final LoginRequest request = new LoginRequest(email, password);

        // when
        final String token = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/login/customer")
                .then().log().all()
                .statusCode(200)
                .extract()
                .header(HttpHeaders.SET_COOKIE)
                .split(";")[0]
                .split(TokenCookieService.COOKIE_TOKEN_KEY + "=")[1];

        final String actual = jwtHandler.decode(token, JwtHandler.CLAIM_ID_KEY);

        // then
        assertThat(actual).isEqualTo("1.0");
    }
}
