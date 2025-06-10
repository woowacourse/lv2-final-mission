package finalmission.integration;

import finalmission.member.dto.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql("init.sql")
public class LoginIntegrationTest {

    @DisplayName("등록된 회원이라면 로그인을 할 수 있다")
    @Test
    void loginMemberTest() {
        LoginRequest loginRequestDto = new LoginRequest("jumdo12@gmail.com", "1234");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginRequestDto)
                .when().post("/login")
                .then().log().all()
                .statusCode(200);
    }
}
