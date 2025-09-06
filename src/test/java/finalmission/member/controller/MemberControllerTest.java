package finalmission.member.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void signupTest() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("email", "brown@gmail.com");
        params.put("password", "wooteco7");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/signup")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    void loginTest() {
        Map<String, Object> signupParam = new HashMap<>();
        signupParam.put("name", "logintest");
        signupParam.put("email", "logintest@gmail.com");
        signupParam.put("password", "logintest");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(signupParam)
                .when().post("/signup")
                .then().log().all()
                .statusCode(201);

        Map<String, Object> loginParam = new HashMap<>();
        loginParam.put("email", "logintest@gmail.com");
        loginParam.put("password", "logintest");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginParam)
                .when().post("/login")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void logoutTest() {
        Map<String, Object> signupParam = new HashMap<>();
        signupParam.put("name", "logout");
        signupParam.put("email", "logout@gmail.com");
        signupParam.put("password", "logout");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(signupParam)
                .when().post("/signup")
                .then().log().all()
                .statusCode(201);

        Map<String, Object> loginParam = new HashMap<>();
        loginParam.put("email", "logout@gmail.com");
        loginParam.put("password", "logout");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginParam)
                .when().post("/login")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/logout")
                .then().log().all()
                .statusCode(200);
    }
}