package finalmission.room.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RoomControllerTest {

    @Test
    @DisplayName("방 목록을 조회한다")
    public void test1() {
        RestAssured.given().log().all()
                .when().get("/rooms")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3));
    }
}
