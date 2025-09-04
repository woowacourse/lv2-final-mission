package finalmission.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import finalmission.domain.Coach;
import finalmission.domain.HolidayExtractor;
import finalmission.dto.request.CoachLoginRequest;
import finalmission.dto.request.UpdateMeetingTimeRequest;
import finalmission.support.Fixture;
import finalmission.support.IntegrationTestSupport;
import finalmission.repository.CoachRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

class CoachControllerTest extends IntegrationTestSupport {

    @Autowired
    private CoachRepository coachRepository;

    @MockitoBean
    private HolidayExtractor holidayExtractor;

    @LocalServerPort
    int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @DisplayName("코치는 로그인할 수 있다.")
    @Test
    void login() {
        // given
        String authId = "wade";
        String password = "password";
        Coach coach = Fixture.createCoach(authId, password);
        coachRepository.save(coach);

        CoachLoginRequest request = new CoachLoginRequest(authId, password);

        // when & then
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/coaches/login")
                .then()
                .statusCode(OK.value());
    }

    @DisplayName("모든 코치를 조회할 수 있다.")
    @Test
    void getAll() {
        // given
        Coach coach1 = Fixture.createCoach();
        Coach coach2 = Fixture.createCoach();

        coachRepository.save(coach1);
        coachRepository.save(coach2);

        // when & then
        RestAssured.given()
                .when()
                .get("/coaches")
                .then()
                .statusCode(OK.value());
    }

    @DisplayName("특정 코치를 상세 조회할 수 있다.")
    @Test
    void getById() {
        // given
        Coach coach = Fixture.createCoach();
        Coach savedCoach = coachRepository.save(coach);

        given(holidayExtractor.extract(anyInt(), anyInt())).willReturn(List.of());

        // when & then
        RestAssured.given()
                .when()
                .get("/coaches/{id}", savedCoach.getId())
                .then()
                .statusCode(OK.value());
    }

    @DisplayName("존재하지 않는 코치 조회 시 예외가 발생한다.")
    @Test
    void getByIdNotFound() {
        // given
        Long nonExistentId = -1L;

        // when & then
        RestAssured.given()
                .when()
                .get("/coaches/{id}", nonExistentId)
                .then()
                .statusCode(BAD_REQUEST.value());
    }

    @DisplayName("코치는 미팅 시간을 수정할 수 있다")
    @Test
    void updateMeetingTime() {
        // given
        String authId = "wade";
        String password = "password";
        Coach coach = Fixture.createCoach(authId, password);
        coachRepository.save(coach);

        CoachLoginRequest loginRequest = new CoachLoginRequest(authId, password);
        String token = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .post("/coaches/login")
                .jsonPath().getString("token");

        UpdateMeetingTimeRequest updateRequest = new UpdateMeetingTimeRequest(
                LocalTime.of(10, 0),
                LocalTime.of(19, 0)
        );

        // when & then
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(updateRequest)
                .when()
                .patch("/admin/coaches/meet-time")
                .then()
                .statusCode(NO_CONTENT.value());
    }
}
