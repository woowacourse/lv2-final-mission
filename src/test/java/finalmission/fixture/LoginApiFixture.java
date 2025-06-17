package finalmission.fixture;

import finalmission.auth.ui.dto.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class LoginApiFixture {

    private LoginApiFixture() {
    }

    public static Map<String, String> memberLoginAndGetCookies(final LoginRequest loginRequest) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when().post("/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().cookies();
    }

    public static Map<String, String> adminLoginAndGetCookies() {
        final LoginRequest loginRequest = new LoginRequest("wooheiler@gmail.com", "admin");

        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when().post("/login")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().cookies();
    }
}
