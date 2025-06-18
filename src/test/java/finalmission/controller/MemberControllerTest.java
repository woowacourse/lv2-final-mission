package finalmission.controller;

import finalmission.member.controller.dto.request.MemberRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MemberControllerTest {

    private MemberRequest request;

    @BeforeEach
    public void setUp() {
        request = new MemberRequest(
                "ywcsuwon@naver.com",
                "123123",
                "Lemon"
        );
    }

    @Test
    public void test() {
        RestAssured.given().log().all()
                .contentType("application/json")
                .body(request)
                .when().post("/member")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }
}
