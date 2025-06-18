package finalmission.controller;

import finalmission.reservationTime.controller.dto.request.ReservationTimeRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TimeControllerTest {

    private ReservationTimeRequest request;

    @BeforeEach
    public void setUp() {
        request = new ReservationTimeRequest("17:00");
    }


    @Test
    void test(){
        RestAssured.given().log().all()
                .contentType("application/json")
                .body(request)
                .when().post("/time")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

}
