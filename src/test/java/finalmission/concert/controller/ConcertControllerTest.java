package finalmission.concert.controller;

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
class ConcertControllerTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String memberToken;
    private String adminToken;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("INSERT INTO member (name, email, password, role) VALUES ('시소', 'siso@gmail.com', '1234', 'MEMBER')");
        jdbcTemplate.update("INSERT INTO member (name, email, password, role) VALUES ('솔라', 'solar@gmail.com', '1234', 'ADMIN')");
        memberToken = authService.login(new LoginRequest("siso@gmail.com", "1234"));
        adminToken = authService.login(new LoginRequest("solar@gmail.com", "1234"));

        jdbcTemplate.update("INSERT INTO venue (name, address) VALUES ('올림픽공원 KSPO 돔', '서울특별시 송파구 올림픽로 424')");
    }

    @Test
    void 콘서트가_정상적으로_생성된다() {
        final Map<Object, Object> params = new HashMap<>();
        params.put("title", "시소의 공연");
        params.put("artist", "싯소");
        params.put("concertDate", "2025-06-18T20:00:00");
        params.put("venueId", 1L);
        params.put("price", "100000");
        params.put("description", "시소가 춤추고 노래한당");

        RestAssured.given().log().all()
                .cookie("token", adminToken)
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/concerts")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        RestAssured.given().log().all()
                .cookie("token", memberToken)
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/concerts")
                .then().log().all()
                .statusCode(403);
    }

    @Test
    void 콘서트가_정상적으로_조회된다() {
        jdbcTemplate.update("INSERT INTO concert (title, artist, concert_date, venue_id, price, description) VALUES ('아이유 콘서트', '아이유', '2025-07-20 19:00:00', 1, 120000, '아이유의 2025년 단독 콘서트')");

        RestAssured.given().log().all()
                .cookie("token", memberToken)
                .contentType(ContentType.JSON)
                .when().get("/concerts/1")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));
    }

    @Test
    void 전체_콘서트가_정상적으로_조회된다() {
        RestAssured.given().log().all()
                .cookie("token", memberToken)
                .contentType(ContentType.JSON)
                .when().get("/concerts")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 시작_전인_콘서트를_조회한다() {
        RestAssured.given().log().all()
                .cookie("token", memberToken)
                .contentType(ContentType.JSON)
                .when().get("/concerts/before")
                .then().log().all()
                .statusCode(200);
    }
}
