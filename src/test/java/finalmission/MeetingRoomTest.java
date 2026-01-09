package finalmission;

import static org.hamcrest.Matchers.blankString;
import static org.hamcrest.Matchers.not;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MeetingRoomTest {

    @Test
    @DisplayName("회의실을 생성한다")
    void create() {
        RestAssured.given()
            .when().post("/meetingrooms")
            .then()
            .statusCode(200)
            .body("name", not(blankString()));
    }
}
