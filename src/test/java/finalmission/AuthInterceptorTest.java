package finalmission;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class AuthInterceptorTest {

    @Test
    @DisplayName("토큰이 필요한 경로에 대한 요청에 토큰이 포함되지 않으면 500 을 반환한다")
    void test1() {
        RestAssured.given()
                .when().post("/reservation")
                .then().log().all()
                .statusCode(500);
    }

    @Test
    @DisplayName("토큰이 필요하지 않는 경로에 대한 요청인 경우 토큰이 포함되지 않아도 예외가 발생하지 않는다")
    void test2() {
        // given
        Map<String, Object> params = new HashMap<>();
        params.put("name", "히로");
        params.put("email", "example@gmail.com");
        params.put("password", "123456");

        // when & then
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/member")
                .then().log().all()
                .statusCode(200);
    }
}
