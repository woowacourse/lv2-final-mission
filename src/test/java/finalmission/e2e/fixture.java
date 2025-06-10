package finalmission.e2e;

import finalmission.member.dto.LoginRequest;
import finalmission.member.dto.SignupRequest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import org.springframework.http.MediaType;

public class fixture {

    public static final String REGULAR_EMAIL = "regular@gmail.com";
    public static final String REGULAR_NAME = "regular";
    public static final String TOKEN = "token";
    public static final String PASSWORD = "password";

    public static String loginRegular() {
        RestAssured.given().log().all()
                .body(new SignupRequest(REGULAR_NAME, REGULAR_EMAIL, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/member/signup")
                .then().log().all()
                .statusCode(201)
                .extract()
                .as(new TypeRef<>() {
                });

        return RestAssured.given().log().all()
                .body(new LoginRequest(REGULAR_EMAIL, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/member/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .cookie(TOKEN);
    }
}
