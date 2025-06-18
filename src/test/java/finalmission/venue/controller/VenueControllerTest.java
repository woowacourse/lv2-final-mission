package finalmission.venue.controller;

import finalmission.member.controller.dto.LoginRequest;
import finalmission.member.service.AuthService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class VenueControllerTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String memberToken;
    private String adminToken;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update(
                "INSERT INTO member (name, email, password, role) VALUES ('시소', 'siso@gmail.com', '1234', 'MEMBER')");
        jdbcTemplate.update(
                "INSERT INTO member (name, email, password, role) VALUES ('솔라', 'solar@gmail.com', '1234', 'ADMIN')");
        memberToken = authService.login(new LoginRequest("siso@gmail.com", "1234"));
        adminToken = authService.login(new LoginRequest("solar@gmail.com", "1234"));
    }

    @Test
    void 공연장이_정상적으로_생성된다() {
        final Map<Object, Object> params = new HashMap<>();
        params.put("name", "루터 회관");
        params.put("address", "잠실 오딘가");

        RestAssured.given().log().all()
                .cookie("token", adminToken)
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/venues")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        RestAssured.given().log().all()
                .cookie("token", memberToken)
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/venues")
                .then().log().all()
                .statusCode(403);
    }

    @Test
    void 공연장을_조회한다() {
        jdbcTemplate.update("INSERT INTO venue (name, address) VALUES ('올림픽공원 KSPO 돔', '서울특별시 송파구 올림픽로 424')");

        RestAssured.given().log().all()
                .cookie("token", memberToken)
                .contentType(ContentType.JSON)
                .when().get("/venues/1")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));
    }

    @Test
    void 전체_공연장을_조회한다() {
        RestAssured.given().log().all()
                .cookie("token", memberToken)
                .contentType(ContentType.JSON)
                .when().get("/venues")
                .then().log().all()
                .statusCode(200);
    }
}
