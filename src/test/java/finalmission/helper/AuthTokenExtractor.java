package finalmission.helper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Map;

public class AuthTokenExtractor {

    public static String extractMemberToken() {
        return extractToken(Map.of(
                "email", "member1@email.com",
                "password", "password")
        );
    }

    private static String extractToken(Map<String, String> credentials) {
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(credentials)
                .when().post("/auth/login")
                .then().statusCode(200)
                .extract();

        String token = response.cookie("token");
        if (token == null) {
            throw new IllegalStateException("응답에 토큰이 없습니다.");
        }
        return token;
    }
}
