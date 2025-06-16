package finalmission.presentation;

import static org.hamcrest.Matchers.is;

import finalmission.TestConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Import(TestConfig.class)
public class CoachControllerTest {

    @Test
    @DisplayName("코치 조회 테스트")
    void getCoachTest(){
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/coachs")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(19));
    }
}
