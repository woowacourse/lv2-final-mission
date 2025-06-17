package finalmission;


import static org.assertj.core.api.Assertions.assertThat;

import finalmission.dto.CreateReservationRequest;
import finalmission.dto.CreateReservationResponse;
import finalmission.dto.ScheduleResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Sql("/test-restaurant-data.sql")
public class ReservationApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("POST /schedules/{id}/reservations")
    @Test
    void createReservation() {
        // given
        CreateReservationRequest request = new CreateReservationRequest(
                "사용자3",
                "juheedorothy@naver.com",
                "1234",
                5,
                "5명 예약이요"
        );
        CreateReservationResponse expectedResponse = new CreateReservationResponse(
                3L,
                5,
                "juheedorothy@naver.com",
                "5명 예약이요",
                "사용자3",
                "1234",
                new ScheduleResponse(1L, LocalDate.of(2025, 6, 11), LocalTime.of(13, 0), 0)
        );
        // when
        CreateReservationResponse actualResponse = RestAssured.given().log().all()
                .body(request)
                .contentType(ContentType.JSON)
                .when().post("/schedules/{id}/reservations", 1L)
                .then().log().all()        // then
                .statusCode(201)
                .extract().as(CreateReservationResponse.class);
        // then
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}
