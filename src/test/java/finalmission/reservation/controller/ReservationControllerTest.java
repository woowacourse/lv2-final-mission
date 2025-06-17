package finalmission.reservation.controller;

import finalmission.accommodation.dto.CreateAccommodationRequest;
import finalmission.auth.dto.LoginRequest;
import finalmission.dateprice.dto.AddDatePriceRequest;
import finalmission.member.domain.Role;
import finalmission.member.dto.RegisterRequest;
import finalmission.reservation.dto.CreateReservationRequest;
import finalmission.reservation.dto.DeleteReservationRequest;
import finalmission.reservation.dto.EditReservationRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
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
class ReservationControllerTest {

    private String token;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        RegisterRequest registerRequest = new RegisterRequest("test@email.com", "password", Role.ADMIN);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(registerRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(201);

        LoginRequest loginRequest = new LoginRequest("test@email.com", "password");
        token = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when().post("/login")
                .then().log().all()
                .extract().cookie("token");
    }

    @Test
    void 특정_기간에_대해_예약을_생성할_수_있다() {
        // given
        setAccommodation();
        setDatePrices();

        CreateReservationRequest request = new CreateReservationRequest(
                LocalDate.of(2025, 6, 11),
                LocalDate.of(2025, 6, 14),
                "예약자 이름",
                "010-1234-5678",
                "oz122@naver.com",
                1L
        );

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .body("id", Matchers.equalTo(1))
                .body("totalPrice", Matchers.equalTo(60000));
    }

    @Test
    void 특정_기간에_대해_예약_목록을_볼_수_있다() {
        // given
        setAccommodation();
        setDatePrices();
        setReservation();

        // when & then
        RestAssured.given().log().all()
                .when().get("/reservations?year=2025&month=6")
                .then().log().all()
                .statusCode(200)
                .body("size()", Matchers.is(1));
    }

    @Test
    void 예약자_정보로_예약을_조회할_수_있다() {
        // given
        setAccommodation();
        setDatePrices();
        setReservation();

        // when & then
        RestAssured.given().log().all()
                .when().get("/reservations/1?name=예약자 이름&phoneNumber=010-1234-5678")
                .then().log().all()
                .statusCode(200)
                .body("id", Matchers.is(1));
    }

    @Test
    void 예약자_정보가_일치하지_않으면_예외가_발생한다() {
        // given
        setAccommodation();
        setDatePrices();
        setReservation();

        // when & then
        RestAssured.given().log().all()
                .when().get("/reservations/1?name=예약자아닌이름&phoneNumber=010-1234-5678")
                .then().log().all()
                .statusCode(403);
    }

    @Test
    void 예약자_정보를_수정할_수_있다() {
        // given
        setAccommodation();
        setDatePrices();
        setReservation();

        EditReservationRequest request = new EditReservationRequest(
                1L,
                "예약자 이름",
                "010-1234-5678",
                "새로운 이름",
                null
        );

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().patch("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("customer.name", Matchers.equalTo("새로운 이름"))
                .body("customer.phoneNumber", Matchers.equalTo("010-1234-5678"));
    }

    @Test
    void 본인의_예약을_삭제할_수_있다() {
        // given
        setAccommodation();
        setDatePrices();
        setReservation();

        DeleteReservationRequest request = new DeleteReservationRequest(1L,
                "예약자 이름",
                "010-1234-5678"
        );

        // when
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().delete("/reservations")
                .then().log().all()
                .statusCode(204);

        // then
        RestAssured.given().log().all()
                .when().get("/reservations/1?name=예약자 이름&phoneNumber=010-1234-5678")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void 관리자는_특정_숙소의_전체_예약_목록을_확인할_수_있다() {
        // given
        setAccommodation();
        setDatePrices();
        setReservation();

        // when & then
        RestAssured.given().log().all()
                .cookie("token", token)
                .when().get("/accommodations/1/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1));
    }

    @Test
    void 관리자는_특정_예약의_상세_정보를_조회할_수_있다() {
        // given
        setAccommodation();
        setDatePrices();
        setReservation();

        // when & then
        RestAssured.given().log().all()
                .cookie("token", token)
                .when().get("/admin/reservations/1")
                .then().log().all()
                .statusCode(200)
                .body("id", Matchers.equalTo(1));
    }

    void setAccommodation() {
        CreateAccommodationRequest accommodationRequest = new CreateAccommodationRequest("숙소 이름", "숙소 설명", "숙소 주소");
        RestAssured.given().log().all()
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .body(accommodationRequest)
                .when().post("/accommodations")
                .then().log().all()
                .statusCode(201);
    }

    void setDatePrices() {
        long accommodationId = 1;
        for (int i = 1; i <= 4; i++) { // 11일 ~ 14일에 대해 가격 등록 : 10000, 20000, 30000, 40000
            AddDatePriceRequest request = new AddDatePriceRequest(LocalDate.of(2025, 6, 10 + i), 10000L * i,
                    accommodationId);
            RestAssured.given().log().all()
                    .cookie("token", token)
                    .contentType(ContentType.JSON)
                    .body(request)
                    .when().post("/date-price")
                    .then().log().all()
                    .statusCode(201);
        }
    }

    void setReservation() {
        CreateReservationRequest request = new CreateReservationRequest(
                LocalDate.of(2025, 6, 11),
                LocalDate.of(2025, 6, 14),
                "예약자 이름",
                "010-1234-5678",
                "oz122@naver.com",
                1L
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);
    }
}
