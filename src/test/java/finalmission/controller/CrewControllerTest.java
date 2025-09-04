package finalmission.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import finalmission.domain.Crew;
import finalmission.domain.EducationPart;
import finalmission.dto.request.CrewLoginRequest;
import finalmission.dto.request.CrewSignUpRequest;
import finalmission.repository.CrewRepository;
import finalmission.support.Fixture;
import finalmission.support.IntegrationTestSupport;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

class CrewControllerTest extends IntegrationTestSupport {

    @Autowired
    private CrewRepository crewRepository;

    @LocalServerPort
    int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @DisplayName("크루 회원가입을 할 수 있다.")
    @Test
    void signUp() {
        // given
        CrewSignUpRequest signUpRequest = new CrewSignUpRequest(
                "wade",
                "email",
                "password",
                7,
                EducationPart.BACKEND
        );

        // when & then
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when()
                .post("/crews/sign-up")
                .then()
                .statusCode(CREATED.value());
    }

    @DisplayName("크루 정보로 로그인할 수 있다.")
    @Test
    void login() {
        // given
        String email = "email";
        String password = "password";
        Crew crew = Fixture.createCrew(email, password);
        crewRepository.save(crew);

        CrewLoginRequest loginRequest = new CrewLoginRequest(email, password);

        // when & then
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/crews/login")
                .then()
                .statusCode(OK.value());
    }
}
