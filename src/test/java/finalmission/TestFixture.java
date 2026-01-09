package finalmission;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

public class TestFixture {
    public static String login() {
        Map<String, String> params = new HashMap<>();
        params.put("email", "123");
        params.put("password", "123");

        Response response = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/login")
            .then()
            .statusCode(200)
            .extract().response();

        return response.cookie("token");
    }

    public static String login2() {
        Map<String, String> params = new HashMap<>();
        params.put("email", "234");
        params.put("password", "123");

        Response response = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/login")
            .then()
            .statusCode(200)
            .extract().response();

        return response.cookie("token");
    }
}
