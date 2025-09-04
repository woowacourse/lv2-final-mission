package finalmission.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import finalmission.domain.Coach;
import finalmission.domain.Crew;
import finalmission.domain.Meeting;
import finalmission.domain.MeetingStatus;
import finalmission.dto.request.CoachLoginRequest;
import finalmission.dto.request.CreateMeetingRequest;
import finalmission.dto.request.CrewLoginRequest;
import finalmission.dto.request.MeetingAnswerRequest;
import finalmission.dto.request.UpdateMeetingRequest;
import finalmission.support.Fixture;
import finalmission.support.IntegrationTestSupport;
import finalmission.repository.CoachRepository;
import finalmission.repository.CrewRepository;
import finalmission.repository.MeetingRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

class MeetingControllerTest extends IntegrationTestSupport {

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @LocalServerPort
    int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @DisplayName("미팅을 생성할 수 있다.")
    @Test
    void create() {
        // given
        Coach coach = Fixture.createCoach();
        Coach savedCoach = coachRepository.save(coach);

        String email = "email";
        String password = "password";
        Crew crew = Fixture.createCrew(email, password);
        crewRepository.save(crew);

        CrewLoginRequest loginRequest = new CrewLoginRequest(email, password);
        String crewToken = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .post("/crews/login")
                .jsonPath().getString("token");

        CreateMeetingRequest createRequest = new CreateMeetingRequest(
                savedCoach.getId(),
                LocalDate.of(2025, 7, 1),
                LocalTime.of(14, 0),
                "미팅 내용"
        );
        System.out.println(crewToken);
        // when & then
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + crewToken)
                .body(createRequest)
                .when()
                .post("/user/meetings")
                .then()
                .statusCode(CREATED.value());
    }

    @DisplayName("코치는 자신의 모든 미팅을 조회할 수 있다")
    @Test
    void getAllMeetingApplicantByCoach() {
        // given
        String authId = "authId";
        String password = "password";
        Coach coach = Fixture.createCoach(authId, password);
        Coach savedCoach = coachRepository.save(coach);

        Crew crew = Fixture.createCrew();
        Crew savedCrew = crewRepository.save(crew);

        Meeting meeting = Fixture.createPendingMeeting(savedCrew, savedCoach);
        meetingRepository.save(meeting);

        CoachLoginRequest loginRequest = new CoachLoginRequest(authId, password);
        String coachToken = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .post("/coaches/login")
                .jsonPath().getString("token");

        // when & then
        RestAssured.given()
                .header("Authorization", "Bearer " + coachToken)
                .when()
                .get("/admin/coaches/meetings")
                .then()
                .statusCode(OK.value());
    }

    @DisplayName("크루는 자신의 모든 미팅을 조회할 수 있다.")
    @Test
    void getAllByCrewId() {
        // given
        Coach coach = Fixture.createCoach();
        Coach savedCoach = coachRepository.save(coach);

        String email = "email";
        String password = "password";
        Crew crew = Fixture.createCrew(email, password);
        Crew savedCrew = crewRepository.save(crew);

        Meeting meeting = Fixture.createPendingMeeting(savedCrew, savedCoach);
        meetingRepository.save(meeting);

        CrewLoginRequest loginRequest = new CrewLoginRequest(email, password);
        String crewToken = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .post("/crews/login")
                .jsonPath().getString("token");

        // when & then
        RestAssured.given()
                .header("Authorization", "Bearer " + crewToken)
                .when()
                .get("/user/crews/meetings")
                .then()
                .statusCode(OK.value());
    }

    @DisplayName("크루는 특정 미팅을 상세 조회할 수 있다")
    @Test
    void getByIdAndCrewId() {
        // given
        Coach coach = Fixture.createCoach();
        Coach savedCoach = coachRepository.save(coach);

        String email = "email";
        String password = "password";
        Crew crew = Fixture.createCrew(email, password);
        Crew savedCrew = crewRepository.save(crew);

        Meeting meeting = Fixture.createPendingMeeting(savedCrew, savedCoach);
        Meeting savedMeeting = meetingRepository.save(meeting);

        CrewLoginRequest loginRequest = new CrewLoginRequest(email, password);
        String crewToken = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .post("/crews/login")
                .jsonPath().getString("token");

        // when & then
        RestAssured.given()
                .header("Authorization", "Bearer " + crewToken)
                .when()
                .get("/user/meetings/{meetingId}", savedMeeting.getId())
                .then()
                .statusCode(OK.value());
    }

    @DisplayName("미팅을 수정할 수 있다")
    @Test
    void update() {
        // given
        Coach coach = Fixture.createCoach();
        Coach savedCoach = coachRepository.save(coach);

        String email = "email";
        String password = "password";
        Crew crew = Fixture.createCrew(email, password);
        Crew savedCrew = crewRepository.save(crew);

        Meeting meeting = Fixture.createPendingMeeting(savedCrew, savedCoach);
        Meeting savedMeeting = meetingRepository.save(meeting);

        CrewLoginRequest loginRequest = new CrewLoginRequest(email, password);
        String crewToken = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .post("/crews/login")
                .jsonPath().getString("token");

        UpdateMeetingRequest updateRequest = new UpdateMeetingRequest("수정된 미팅 내용");

        // when & then
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + crewToken)
                .body(updateRequest)
                .when()
                .put("/user/meetings/{meetingId}", savedMeeting.getId())
                .then()
                .statusCode(NO_CONTENT.value());
    }

    @DisplayName("코치는 미팅 신청에 대해 답할 수 있다.")
    @Test
    void answer() {
        // given
        String authId = "authId";
        String password = "password";
        Coach coach = Fixture.createCoach(authId, password);
        Coach savedCoach = coachRepository.save(coach);

        Crew crew = Fixture.createCrew();
        Crew savedCrew = crewRepository.save(crew);

        Meeting meeting = Fixture.createPendingMeeting(savedCrew, savedCoach);
        Meeting savedMeeting = meetingRepository.save(meeting);

        MeetingAnswerRequest answerRequest = new MeetingAnswerRequest(MeetingStatus.ACCEPT);

        CoachLoginRequest loginRequest = new CoachLoginRequest(authId, password);
        String coachToken = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .post("/coaches/login")
                .jsonPath().getString("token");

        // when & then
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + coachToken)
                .body(answerRequest)
                .when()
                .patch("/admin/meetings/{meetingId}", savedMeeting.getId())
                .then()
                .statusCode(NO_CONTENT.value());
    }
}
