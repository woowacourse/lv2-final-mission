package shh.login.ui;

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
import shh.login.application.JwtService;
import shh.login.application.TokenCookieService;
import shh.login.application.dto.LoginRequest;

@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class LoginApiTest {

    @LocalServerPort
    private int port;

    private final JwtService jwtService;

    @Autowired
    public LoginApiTest(final JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 로그인에_성공하면_JWT_accessToken을_받는다() {
        // given
        final String email = "test1@email.com";
        final String password = "1234";

        final LoginRequest request = new LoginRequest(email, password);

        // when
        final String token = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .header(HttpHeaders.SET_COOKIE)
                .split(";")[0]
                .split(TokenCookieService.COOKIE_TOKEN_KEY + "=")[1];

        final String actual = jwtService.decode(token).get(JwtService.CLAIM_ID_KEY);

        // then
        assertThat(actual).isEqualTo("1");
    }


}
