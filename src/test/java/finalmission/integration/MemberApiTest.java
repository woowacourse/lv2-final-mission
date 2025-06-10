package finalmission.integration;

import finalmission.controller.MemberSignUpRequest;
import finalmission.domain.LolName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MemberApiTest {

    @DisplayName("회원가입을 한다.")
    @Test
    void signUp() {
        // given
        final MemberSignUpRequest request = new MemberSignUpRequest(
                new LolName("누신누황", "nunu"),
                "qwe123"
        );

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/member/signup")
                .then().log().all()
                .statusCode(200);
    }
}
