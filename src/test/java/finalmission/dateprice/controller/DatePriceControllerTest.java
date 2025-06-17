package finalmission.dateprice.controller;

import finalmission.accommodation.dto.CreateAccommodationRequest;
import finalmission.auth.dto.LoginRequest;
import finalmission.dateprice.dto.AddDatePriceRequest;
import finalmission.member.domain.Role;
import finalmission.member.dto.RegisterRequest;
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
class DatePriceControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 숙소_특정_날짜에_가격을_등록할_수_있다() {
        // given
        RegisterRequest registerRequest = new RegisterRequest("test@email.com", "password", Role.ADMIN);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(registerRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(201);

        LoginRequest loginRequest = new LoginRequest("test@email.com", "password");
        String token = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when().post("/login")
                .then().log().all()
                .extract().cookie("token");

        CreateAccommodationRequest accommodationRequest = new CreateAccommodationRequest("숙소 이름", "숙소 설명", "숙소 주소");
        RestAssured.given().log().all()
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .body(accommodationRequest)
                .when().post("/accommodations")
                .then().log().all()
                .statusCode(201);

        long accommodationId = 1;
        AddDatePriceRequest request = new AddDatePriceRequest(LocalDate.of(2025, 6, 10), 10000L, accommodationId);

        // when & then
        RestAssured.given().log().all()
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/date-price")
                .then().log().all()
                .statusCode(201)
                .body("id", Matchers.equalTo(1));
    }
}
