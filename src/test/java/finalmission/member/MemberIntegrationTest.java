package finalmission.member;

import static org.hamcrest.CoreMatchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class MemberIntegrationTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 회원을_조회할_수_있다() {
        RestAssured.given().log().all()
                .when().get("/members")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(4));
    }

    @Test
    void 회원을_생성할_수_있다() {
        // given
        Map<String, String> params = new HashMap<>();
        params.put("name", "승욘구리");
        params.put("email", "yeonseung@naver.com");
        params.put("password", "1234");

        // when
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/members")
                .then().log().all()
                .statusCode(201)
                .body("email", is("yeonseung@naver.com"));

        RestAssured.given().log().all()
                .when().get("/members")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(5));
    }

//    @Test
//    void 회원을_삭제할_수_있다() {
//        RestAssured.given().log().all()
//                .when().delete("/members/4")
//                .then().log().all()
//                .statusCode(204);
//
//        RestAssured.given().log().all()
//                .when().get("/members")
//                .then().log().all()
//                .statusCode(200)
//                .body("size()", is(3));
//    }
}
