package finalmission.member.controller;

import finalmission.auth.dto.LoginDto;
import finalmission.auth.service.AuthService;
import finalmission.user.User;
import finalmission.user.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;

    @BeforeEach
    void configureRestAssured() {
        RestAssured.port = port;
    }


    @DisplayName("로그인 인터셉터가 잘 동작하는 지 확인")
    @Test
    void loginMemberInterceptor() {
        // given
        User savedUser = userRepository.save(new User("name1", "member1", "email1", "password1"));
        String token = authService.login(new LoginDto("email1", "password1"));

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/members/test")
                .then().log().all()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
