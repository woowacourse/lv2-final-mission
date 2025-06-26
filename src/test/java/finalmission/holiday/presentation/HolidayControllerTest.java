package finalmission.holiday.presentation;

import finalmission.holiday.presentation.dto.request.HolidayCreateWebRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class HolidayControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 공휴일_생성_요청을_보내고_성공_시_201을_반환한다() {
        LocalDate futureDate = LocalDate.now().plusDays(1);
        String name = "공휴일";

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(new HolidayCreateWebRequest(futureDate, name))
                .when()
                    .post("/holidays")
                .then()
                    .statusCode(201)
                    .body(
                            "date", equalTo(futureDate.toString()),
                        "name", equalTo(name)
                    );
    }

    @Test
    void 국가_공휴일을_모두_등록하고_등록이_성공하면_200을_반환한다() {
        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                .when()
                    .post("/holidays/national")
                .then()
                    .statusCode(200);
    }
}