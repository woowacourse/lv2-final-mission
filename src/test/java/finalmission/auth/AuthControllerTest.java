package finalmission.auth;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    private final int port;
    private final JdbcTemplate jdbcTemplate;
    private final JwtProvider jwtProvider;

    public AuthControllerTest(
            @LocalServerPort final int port,
            @Autowired JdbcTemplate jdbcTemplate,
            @Autowired JwtProvider jwtProvider) {
        this.port = port;
        this.jdbcTemplate = jdbcTemplate;
        this.jwtProvider = jwtProvider;
    }

    @DisplayName("정상 토큰 쿠키로 /check 요청 시 200")
    @Test
    void cookieMemberCheckTest() {
        // given
        final String phoneNumber = "01012345678";
        givenMember(phoneNumber);
        String token = jwtProvider.provideToken(phoneNumber);

        // then
        RestAssured.given()
                .port(port)
                .cookie("token", token)

                .when().get("auth/check")

                .then().log().all()
                .statusCode(200);

    }

    @DisplayName("비정상 토큰 쿠키로 /check 요청 시 500")
    @Test
    void invalidCookieMemberCheckTest() {
        // given

        // then
        RestAssured.given()
                .port(port)
                .cookie("token", "안녕하세용저는토큰입니다")

                .when().get("auth/check")

                .then().log().all()
                .statusCode(500);

    }

    private void givenMember(String phoneNumber) {
        final String sql = "INSERT INTO member (name, phone_number) VALUES ('name', '" + phoneNumber + "')";
        jdbcTemplate.update(sql);
    }
}
