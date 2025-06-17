package finalmission.helper;

import finalmission.member.dto.request.LoginRequest;
import finalmission.member.dto.request.MemberRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

public class TestHelper {

    private static final String ADMIN_EMAIL = "admin@email.com";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String ADMIN_NAME = "어드민";

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_PREFIX = "Bearer ";

    public static void signUpAsAdmin() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(new MemberRequest(ADMIN_NAME, ADMIN_EMAIL, ADMIN_PASSWORD))
                .when()
                .post("/members/admin")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .response();
    }

    public static String loginAsAdmin() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(ADMIN_EMAIL, ADMIN_PASSWORD))
                .when()
                .post("/auth/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .jsonPath()
                .get("token");
    }

    public static Response get(String path) {
        return RestAssured.given()
                .when()
                .get(path);
    }

    public static Response getWithToken(String path, String token) {
        return RestAssured.given()
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_PREFIX + token)
                .when()
                .get(path);
    }

    public static Response post(String path, Object body) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(path);
    }

    public static Response postWithToken(String path, Object body, String token) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_PREFIX + token)
                .body(body)
                .when()
                .post(path);
    }

    public static Response deleteWithToken(String path, String token) {
        return RestAssured.given()
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_PREFIX + token)
                .when()
                .delete(path);
    }

    public static Response patchWithToken(String path, Object body, String token) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header(AUTHORIZATION_HEADER, AUTHORIZATION_PREFIX + token)
                .body(body)
                .when()
                .patch(path);
    }
}
