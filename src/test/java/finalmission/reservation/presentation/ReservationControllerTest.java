package finalmission.reservation.presentation;

import finalmission.medical.model.TreatmentType;
import finalmission.member.model.Member;
import finalmission.member.presentation.dto.request.MemberCreateWebRequest;
import finalmission.member.presentation.dto.request.MemberLoginWebRequest;
import finalmission.reservation.model.Time;
import finalmission.reservation.presentation.dto.request.ReservationCreateWebRequest;
import finalmission.reservation.presentation.dto.request.ReservationUpdateTreatmentTypeWebRequest;
import finalmission.reservation.presentation.dto.request.TimeCreateWebRequest;
import finalmission.reservation.presentation.dto.response.ReservationGetDetailWebResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 예약을_생성하는_요청을_보내고_성공_시_201을_반환한다() {
        TreatmentType treatmentType = TreatmentType.EXTRACTION;
        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime startAt = LocalTime.now().plusMinutes(1);
        Time time = generateTime(startAt);
        String username = "username";
        String password = "password";
        String name = "프리";
        Member member = generateMember(username, password, name);
        String token = getToken(username, password);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(new ReservationCreateWebRequest(treatmentType, date, time.getId()))
                .when()
                .post("/reservations")
                .then()
                .statusCode(201)
                .body(
                        "name", equalTo(member.getName()),
                        "treatmentType", equalTo(treatmentType.toString()),
                        "date", equalTo(date.toString()),
                        "time", containsString(String.format("%02d:%02d:%02d", time.getStartAt().getHour(), time.getStartAt().getMinute(), time.getStartAt().getSecond()))
                );
    }

    private Time generateTime(LocalTime startAt) {
        return RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new TimeCreateWebRequest(startAt))
                .when()
                    .post("/reservations/times")
                .then()
                    .statusCode(201)
                .extract()
                    .as(Time.class);
    }

    private Member generateMember(String username, String password, String name) {
        return RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new MemberCreateWebRequest(username, password, name))
                .when()
                    .post("/members/signUp")
                .then()
                    .statusCode(201)
                .extract()
                    .as(Member.class);
    }

    private String getToken(String username, String password) {
        return RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new MemberLoginWebRequest(username, password))
                .when()
                    .post("/members/login")
                .then()
                    .statusCode(200)
                .extract()
                    .cookie("token");
    }

    @Test
    void 로그인하지_않았을_경우에는_예약을_생성할_수_없다() {
        TreatmentType treatmentType = TreatmentType.EXTRACTION;
        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime startAt = LocalTime.now().plusMinutes(1);
        Time time = generateTime(startAt);

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new ReservationCreateWebRequest(treatmentType, date, time.getId()))
                .when()
                    .post("/reservations")
                .then()
                    .statusCode(500);
    }

    @Test
    void 모든_예약을_조회하는_요청을_보내고_성공_시_200을_반환한다() {
        예약을_생성하는_요청을_보내고_성공_시_201을_반환한다();
        RestAssured
                .when()
                    .get("/reservations")
                .then()
                    .statusCode(200)
                    .body("", hasSize(1));
    }

    @Test
    void 주어진_기간_내에_존재하는_예약을_조회하는_요청을_보내고_성공_시_200을_반환한다() {
        예약을_생성하는_요청을_보내고_성공_시_201을_반환한다();
        RestAssured
                .given()
                    .param("startDate", LocalDate.now().toString())
                    .param("endDate", LocalDate.now().plusDays(2).toString())
                .when()
                    .get("/reservations/period")
                .then()
                    .statusCode(200)
                    .body("", hasSize(1));

        RestAssured
                .given()
                    .param("startDate", LocalDate.now().plusDays(2).toString())
                    .param("endDate", LocalDate.now().plusDays(3).toString())
                .when()
                    .get("/reservations/period")
                .then()
                    .statusCode(200)
                    .body("", hasSize(0));
    }

    @Test
    void 주어진_멤버의_예약을_모두_조회하고_성공_시_200을_반환한다() {
        예약을_생성하는_요청을_보내고_성공_시_201을_반환한다();
        String name = "프리";
        RestAssured
                .when()
                    .get("/reservations/member/" + name)
                .then()
                    .statusCode(200)
                    .body("", hasSize(1));

        RestAssured
                .when()
                    .get("/reservations/member/" + "invalidName")
                .then()
                    .statusCode(500);
    }

    @Test
    void 현재_요청을_보낸_멤버의_예약을_모두_조회하고_성공_시_200을_반환한다() {
        TreatmentType treatmentType = TreatmentType.EXTRACTION;
        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime startAt = LocalTime.now().plusMinutes(1);
        Time time = generateTime(startAt);
        String username = "username";
        String password = "password";
        String name = "프리";
        Member member = generateMember(username, password, name);
        String token = getToken(username, password);
        ReservationGetDetailWebResponse reservation = RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .cookie("token", token)
                    .body(new ReservationCreateWebRequest(treatmentType, date, time.getId()))
                .when()
                    .post("/reservations")
                .then()
                    .statusCode(201)
                .extract()
                        .as(ReservationGetDetailWebResponse.class);

        RestAssured
                .given()
                    .cookie("token", token)
                .when()
                    .get("/reservations/" + reservation.id())
                .then()
                    .statusCode(200)
                    .body(
                            "id", equalTo(reservation.id().intValue()),
                            "name", equalTo(member.getName()),
                            "treatmentType", equalTo(reservation.treatmentType().toString()),
                            "date", equalTo(reservation.date().toString()),
                            "time", containsString(String.format("%02d:%02d:%02d", time.getStartAt().getHour(), time.getStartAt().getMinute(), time.getStartAt().getSecond()))
                    );

        RestAssured
                .when()
                    .get("/reservations/" + reservation.id())
                .then()
                    .statusCode(500);
    }

    @Test
    void 내_예약의_진료_종류_수정_요청을_보내고_성공_시_200을_반환한다() {
        TreatmentType treatmentType = TreatmentType.EXTRACTION;
        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime startAt = LocalTime.now().plusMinutes(1);
        Time time = generateTime(startAt);
        String username = "username";
        String password = "password";
        String name = "프리";
        generateMember(username, password, name);
        String token = getToken(username, password);
        TreatmentType newTreatmentType = TreatmentType.SCALING;
        ReservationGetDetailWebResponse reservation = RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .cookie("token", token)
                    .body(new ReservationCreateWebRequest(treatmentType, date, time.getId()))
                .when()
                    .post("/reservations")
                .then()
                    .statusCode(201)
                .extract()
                    .as(ReservationGetDetailWebResponse.class);

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                    .cookie("token", token)
                    .body(new ReservationUpdateTreatmentTypeWebRequest(newTreatmentType))
                .when()
                    .patch("/reservations/" + reservation.id())
                .then()
                    .statusCode(200)
                    .body(
                            "treatmentType", equalTo(newTreatmentType.toString()),
                            "date", equalTo(reservation.date().toString()),
                            "time", containsString(String.format("%02d:%02d:%02d", time.getStartAt().getHour(), time.getStartAt().getMinute(), time.getStartAt().getSecond()))
                    );

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .cookie("token", token)
                    .body(new ReservationUpdateTreatmentTypeWebRequest(newTreatmentType))
                .when()
                    .patch("/reservations/" + 500)
                .then()
                    .statusCode(500);

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new ReservationUpdateTreatmentTypeWebRequest(newTreatmentType))
                .when()
                    .patch("/reservations/" + reservation.id())
                .then()
                    .statusCode(500);
    }

    @Test
    void 내_예약을_삭제_요청하여_성공_시_204를_반환한다() {
        TreatmentType treatmentType = TreatmentType.EXTRACTION;
        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime startAt = LocalTime.now().plusMinutes(1);
        Time time = generateTime(startAt);
        String username = "username";
        String password = "password";
        String name = "프리";
        generateMember(username, password, name);
        String token = getToken(username, password);
        ReservationGetDetailWebResponse reservation = RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .cookie("token", token)
                    .body(new ReservationCreateWebRequest(treatmentType, date, time.getId()))
                .when()
                    .post("/reservations")
                .then()
                    .statusCode(201)
                .extract()
                    .as(ReservationGetDetailWebResponse.class);

        RestAssured
                .when()
                    .delete("/reservations/" + reservation.id())
                .then()
                    .statusCode(500);

        RestAssured
                .given()
                    .cookie("token", token)
                .when()
                    .delete("/reservations/" + 500)
                .then()
                    .statusCode(500);

        RestAssured
                .given()
                    .cookie("token", token)
                .when()
                    .delete("/reservations/" + reservation.id())
                .then()
                    .statusCode(204);
    }
}
