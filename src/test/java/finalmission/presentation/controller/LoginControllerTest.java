package finalmission.presentation.controller;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.Fixture;
import finalmission.presentation.dto.CheckLoginRequest;
import finalmission.presentation.dto.LoginRequest;
import finalmission.presentation.dto.MemberDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        Fixture.resetH2TableIds(jdbcTemplate);
    }

    @DisplayName("로그인 성공 시 쿠키에 토큰을 담아 응답한다.")
    @Test
    void login() {
        //given
        jdbcTemplate.update("INSERT INTO member (id, name, email, password) VALUES (1, '브라운', 'brown@email.com', 'brown')");

        //when & then
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new LoginRequest("brown@email.com", "brown"))
                .when().post("/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .cookie("token");
    }

    @DisplayName("회원의 로그인 확인 성공 시 회원정보를 응답한다.")
    @Test
    void checkLogin() {
        //given
        jdbcTemplate.update("INSERT INTO member (id, name, email, password) VALUES (1, '브라운', 'brown@email.com', 'brown')");

        //when & then
        String token = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new LoginRequest("brown@email.com", "brown"))
                .when().post("/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().cookie("token");

        MemberDto member = RestAssured.given().log().all()
                .cookie("token", token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CheckLoginRequest(1))
                .when().get("/login/check")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().body().as(MemberDto.class);

        assertThat(member.id()).isEqualTo(1);
    }
}
