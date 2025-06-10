package finalmission.mungPlan.ui;

import static finalmission.mungPlan.TestFixture.DEFAULT_DATE;
import static finalmission.mungPlan.TestFixture.createSampleUser;

import finalmission.mungPlan.DatabaseCleaner;
import finalmission.mungPlan.domain.PlanDate;
import finalmission.mungPlan.domain.Reservation;
import finalmission.mungPlan.domain.TimeSlot;
import finalmission.mungPlan.domain.TimeSlots;
import finalmission.mungPlan.domain.User;
import finalmission.mungPlan.infra.PlanDateRepository;
import finalmission.mungPlan.infra.ReservationRepository;
import finalmission.mungPlan.infra.UserRepository;
import finalmission.mungPlan.ui.dto.CreateReservationRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

@Commit
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        databaseCleaner.clean();
    }

    @Autowired
    PlanDateRepository planDateRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @DisplayName("예약 생성")
    @Test
    void createReservationAPI() {
        // given
        TimeSlot timeSlot = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(11 ,0));
        TimeSlots timeSlots = new TimeSlots(List.of(timeSlot));

        PlanDate planDate = planDateRepository.save(PlanDate.createNew(DEFAULT_DATE, timeSlots));

        User user = userRepository.save(createSampleUser());


        // when & then
        CreateReservationRequest createReservationRequest =
                new CreateReservationRequest(planDate.getId(), LocalTime.of(10, 0), user.getId());

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(createReservationRequest)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);
    }

    @DisplayName("예약ID로 특정 예약 조회")
    @Test
    void getReservationById() {
        // given
        TimeSlot timeSlot = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(11 ,0));
        TimeSlots timeSlots = new TimeSlots(List.of(timeSlot));

        PlanDate planDate = planDateRepository.save(PlanDate.createNew(DEFAULT_DATE, timeSlots));

        User user = userRepository.save(createSampleUser());

        Reservation saved = reservationRepository.save(Reservation.createNew(planDate, timeSlot, user));

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/reservations/" + saved.getId())
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("유저ID로 해당 유저의 모든 예약 조회")
    @Test
    void getAllReservationsByUserId() {
        // given
        TimeSlot timeSlot1 = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(11 ,0));
        TimeSlot timeSlot2 = new TimeSlot(LocalTime.of(11, 0), LocalTime.of(11 ,0));
        TimeSlots timeSlots = new TimeSlots(List.of(timeSlot1, timeSlot2));

        PlanDate planDate = planDateRepository.save(PlanDate.createNew(DEFAULT_DATE, timeSlots));

        User user = userRepository.save(createSampleUser());

        reservationRepository.save(Reservation.createNew(planDate, timeSlot1, user));
        reservationRepository.save(Reservation.createNew(planDate, timeSlot2, user));

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/reservations/mine/" + user.getId())
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("예약ID로 특정 예약 삭제 - 본인의 예약일 경우에만")
    @Test
    void getReservationById_whenMyReservation() {
        // given
        TimeSlot timeSlot = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(11 ,0));
        TimeSlots timeSlots = new TimeSlots(List.of(timeSlot));

        PlanDate planDate = planDateRepository.save(PlanDate.createNew(DEFAULT_DATE, timeSlots));
        User user = userRepository.save(createSampleUser());
        Reservation saved = reservationRepository.save(Reservation.createNew(planDate, timeSlot, user));

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(user.getId())
                .when().delete("/reservations/" + user.getId() + "/" +saved.getId())
                .then().log().all()
                .statusCode(204);
    }

    @DisplayName("본인의 예약이 아닐 경우 예약ID로 특정 예약 삭제 불가")
    @Test
    void erorr_getReservationById_whenNoPermission() {
        // given
        TimeSlot timeSlot = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(11 ,0));
        TimeSlots timeSlots = new TimeSlots(List.of(timeSlot));

        PlanDate planDate = planDateRepository.save(PlanDate.createNew(DEFAULT_DATE, timeSlots));
        User user = userRepository.save(User.createNew("user1", "유저1"));
        User otherUser = userRepository.save(User.createNew("user2", "유저2"));
        Reservation saved = reservationRepository.save(Reservation.createNew(planDate, timeSlot, user));

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(user.getId())
                .when().delete("/reservations/" + otherUser.getId() + "/" +saved.getId())
                .then().log().all()
                .statusCode(403);
    }


}
