package finalmission.member.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MemberControllerTest {

    @DisplayName("추천 이름을 요청할 수 있다.")
    @Test
    void getRandomName() {
        RestAssured.given().log().all()
                .get("/name")
                .then().log().all()
                .statusCode(200);
    }
}
