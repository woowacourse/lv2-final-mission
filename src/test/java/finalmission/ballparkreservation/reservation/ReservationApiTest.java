package finalmission.ballparkreservation.reservation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationApiTest {

    private final JdbcTemplate jdbcTemplate;
    private final int port;

    public ReservationApiTest(
            @LocalServerPort final int port,
            @Autowired final JdbcTemplate jdbcTemplate
    ) {
        this.port = port;
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM reservation");
        jdbcTemplate.update("DELETE FROM schedule");
        jdbcTemplate.update("DELETE FROM member");

        jdbcTemplate.update("ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.update("ALTER TABLE schedule ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.update("ALTER TABLE member ALTER COLUMN id RESTART WITH 1");
    }

//    @DisplayName("/reservations GET 예약 정보 모두 조회 테스트")
//    @Test
//    void getAll() {
//        // when & then
//        RestAssured.given().port(port).log().all()
//                .when().get("/reservations")
//                .then().statusCode(200)
//                .body()
//    }

    private void createReservationForTest(Map<String, Object> parameter) {
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(parameter)
                .when().post("/reservations")
                .then().statusCode(201);
    }
}
