package finalmission.fixture;

import finalmission.auth.dto.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.util.Map;

public class MemberFixture {

    private static final String ADMIN_EMAIL = "admin@email.com";
    private static final String ADMIN_PASSWORD = "password";

    private static final String USER_EMAIL = "member1@email.com";
    private static final String USER_PASSWORD = "1234";

    public static Map<String, String> loginAdmin() {
        return login(ADMIN_EMAIL, ADMIN_PASSWORD);
    }

    public static Map<String, String> loginUser() {
        return login(USER_EMAIL, USER_PASSWORD);
    }

    private static Map<String, String> login(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);

        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when().post("/login")
                .then()
                .extract().cookies();
    }
}
