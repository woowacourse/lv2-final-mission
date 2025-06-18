package finalmission.exerciescourse;

import static org.hamcrest.CoreMatchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ExerciescourseIntegrationTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 운동_코스를_조회할_수_있다() {
        RestAssured.given().log().all()
                .when().get("/courses")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(4));
    }

    @Test
    void 운동_코스를_생성할_수_있다() {
        // given
        Map<String, String> params = new HashMap<>();
        params.put("name", "런닝");
        params.put("description", "땀뻘뻘");

        // when
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/courses")
                .then().log().all()
                .statusCode(201)
                .body("name", is("런닝"));

        RestAssured.given().log().all()
                .when().get("/courses")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(5));
    }

    @Test
    void 운동_코스를_삭제할_수_있다() {
        RestAssured.given().log().all()
                .when().delete("courses/4")
                .then().log().all()
                .statusCode(204);

        RestAssured.given().log().all()
                .when().get("/courses")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3));
    }
}
