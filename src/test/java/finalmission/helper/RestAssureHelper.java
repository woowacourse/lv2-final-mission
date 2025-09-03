package finalmission.helper;


import finalmission.member.dto.request.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

public class RestAssureHelper {

    public static String getLoginToken(String email, String password) {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(new LoginRequest(email, password))
                .when()
                .post("/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        return response.getCookie("token");
    }
}
