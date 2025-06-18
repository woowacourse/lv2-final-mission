package finalmission.member.controller;

import finalmission.member.controller.dto.LoginRequest;
import finalmission.member.service.AuthService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.is;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthControllerTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AuthService authService;

    @Test
    void 회원가입에_성공한다() {
        final Map<String, String> params = new HashMap<>();
        params.put("name", "gangsan");
        params.put("email", "gangsan@gmail.com");
        params.put("password", "1234");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/signup")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));
    }

    @Test
    void 이메일_형식이_올바르지_않다면_회원가입에_실패한다() {
        final Map<String, String> params = new HashMap<>();
        params.put("name", "gangsan");
        params.put("email", "gangsangmail.com");
        params.put("password", "1234");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/signup")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void 로그인에_성공한다() {
        jdbcTemplate.update("INSERT INTO member (name, email, password, role) VALUES ('시소', 'siso@gmail.com', '1234', 'MEMBER')");

        final Map<String, String> params = new HashMap<>();
        params.put("email", "siso@gmail.com");
        params.put("password", "1234");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/login")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 로그아웃에_성공한다() {
        jdbcTemplate.update("INSERT INTO member (name, email, password, role) VALUES ('시소', 'siso@gmail.com', '1234', 'MEMBER')");

        final String token = authService.login(new LoginRequest("siso@gmail.com", "1234"));

        RestAssured.given().log().all()
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .when().post("/logout")
                .then().log().all()
                .statusCode(200);
    }
}
