package finalmission.integration;

import finalmission.customer.dto.request.CustomerCreateRequest;
import finalmission.customer.dto.response.CustomerResponse;
import finalmission.reservation.dto.request.ReservationRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationIntegrationTest {

    @DisplayName("예약을 생성할 수 있다.")
    @Test
    void createReservation() {
        CustomerCreateRequest createRequest = new CustomerCreateRequest("id","password");

        CustomerResponse customerResponse = RestAssured.given().log().all()
                .contentType(ContentType.JSON).body(createRequest)
                .when().post("/customers")
                .then().extract().response().as(CustomerResponse.class);

        LocalDate reservationDate = LocalDate.now();
        ReservationRequest reservationRequest = new ReservationRequest(customerResponse.userId(), reservationDate);

        RestAssured.given()
                .when().post("/umbrellas");
        RestAssured.given()
                .when().post("/umbrellas");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON).body(reservationRequest)
                .when().post("/reservations")
                .then().statusCode(201);
    }

    @DisplayName("우산이 이미 모두 예약 되었으면 예약을 생성할 수 없다.")
    @Test
    void outOfUmbrellaReservation() {
        CustomerCreateRequest createRequest = new CustomerCreateRequest("id","password");

        CustomerResponse customerResponse = RestAssured.given().log().all()
                .contentType(ContentType.JSON).body(createRequest)
                .when().post("/customers")
                .then().extract().response().as(CustomerResponse.class);

        LocalDate reservationDate = LocalDate.now();
        ReservationRequest reservationRequest = new ReservationRequest(customerResponse.userId(), reservationDate);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON).body(reservationRequest)
                .when().post("/reservations")
                .then().statusCode(500);
    }
}
