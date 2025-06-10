package finalmission.ballparkreservation.auth;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthApiTest {

    private final JdbcTemplate jdbcTemplate;
    private final int port;

    public AuthApiTest(
            @LocalServerPort final int port,
            @Autowired final JdbcTemplate jdbcTemplate
    ) {
        this.port = port;
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM member");
        jdbcTemplate.update("ALTER TABLE member ALTER COLUMN id RESTART WITH 1");
    }

    @DisplayName("/auth/signup POST 회원가입 테스트")
    @Test
    void signup() {
        // when & then
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(Map.of("email", "may@gmail.com", "password", "1234", "name", "메이", "age", 24))
                .when().post("/auth/signup")
                .then().statusCode(201);
    }

    @DisplayName("/auth/login POST 로그인 테스트")
    @Test
    void login1() {
        // given
        Map<String, Object> parameters = Map.of("email", "may@gmail.com", "password", "1234", "name", "메이", "age", 24);
        signupForTest(parameters);

        // when & then
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(Map.of("email", "may@gmail.com", "password", "1234"))
                .when().post("/auth/login")
                .then().statusCode(200)
                .header(HttpHeaders.SET_COOKIE.toString(), notNullValue());
    }

    @DisplayName("/auth/login POST 로그인 실패 테스트")
    @Test
    void login2() {
        // given
        Map<String, Object> parameters = Map.of("email", "may@gmail.com", "password", "1234", "name", "메이", "age", 24);
        signupForTest(parameters);

        // when & then
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(Map.of("email", "may@gmail.com", "password", "abcd"))
                .when().post("/auth/login")
                .then().statusCode(500) // TODO : 로그인 실패 상태 코드 변경
                .header(HttpHeaders.SET_COOKIE.toString(), nullValue());
    }

    private void signupForTest(Map<String, Object> parameter) {
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(parameter)
                .when().post("/auth/signup")
                .then().statusCode(201);
    }
}
