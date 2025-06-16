package finalmission.omakase.controller;

import static finalmission.omakase.entity.Rating.HIGH_END;
import static org.junit.jupiter.api.Assertions.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class OmakaseControllerTest {

    @LocalServerPort
    private int port;

    private Map<String, Object> omakase;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        omakase = new HashMap<>();
        omakase.put("name", "sushiJun");
        omakase.put("rating", HIGH_END);
    }

    @Test
    @DisplayName("오마카세 업장을 등록한다.")
    void saveTest() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(omakase)
                .when().post("/omakases")
                .then().log().all()
                .statusCode(201);
    }
}
