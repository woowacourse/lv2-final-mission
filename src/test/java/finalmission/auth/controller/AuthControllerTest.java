package finalmission.auth.controller;

import finalmission.auth.dto.LoginRequest;
import finalmission.member.domain.Role;
import finalmission.member.dto.RegisterRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Sql(scripts = "/clean.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class AuthControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 로그인할_수_있다() {
        // given
        LoginRequest request = new LoginRequest("test@email.com", "password");

        RegisterRequest registerRequest = new RegisterRequest("test@email.com", "password", Role.ADMIN);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(registerRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(201);

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/login")
                .then().log().all()
                .statusCode(200)
                .header("Set-Cookie", Matchers.notNullValue());
    }
}
