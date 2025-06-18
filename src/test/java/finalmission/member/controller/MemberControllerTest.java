package finalmission.member.controller;

import finalmission.member.dto.MemberSignUpRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MemberControllerTest {

    @Test
    void 회원가입을_진행한다() {
        MemberSignUpRequest request = new MemberSignUpRequest("test@email.com", "테스트1", "1234");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/members/signUp")
                .then().log().all()
                .statusCode(201);
    }
}
