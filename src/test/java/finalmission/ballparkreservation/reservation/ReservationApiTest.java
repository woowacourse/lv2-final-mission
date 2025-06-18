package finalmission.ballparkreservation.reservation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.Map;

import static org.hamcrest.Matchers.is;

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

        jdbcTemplate.update("INSERT INTO schedule(number, rank, date) VALUES (1, 'TABLE', '2025-05-05')");
        jdbcTemplate.update("INSERT INTO schedule(number, rank, date) VALUES (2, 'TABLE', '2025-05-05')");
        jdbcTemplate.update("INSERT INTO member(email, password, name, age) VALUES ('may@gmail.com', '1234', '메이', 13)");
    }

    @DisplayName("/reservations POST 예약 생성 테스트")
    @Test
    void create1() {
        Cookie tokenCookie = loginForTest();

        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .cookie(tokenCookie)
                .body(Map.of("seatRank", "TABLE", "seatNumber", 1, "date", LocalDate.of(2025, 5, 5)))
                .when().post("/reservations")
                .then().statusCode(201);
    }

    @DisplayName("/reservations GET 모든 예약 조회 테스트")
    @Test
    void getAll1() {
        Cookie cookie = loginForTest();
        createReservationForTest(cookie);

        RestAssured.given().port(port).log().all()
                .cookie(cookie)
                .when().get("/reservations")
                .then().statusCode(200)
                .body("size()", is(1));
    }

    @DisplayName("/reservations/my GET 회원별 예약 조회 테스트")
    @Test
    void getAllByMember1() {
        Cookie cookie = loginForTest();
        createReservationForTest(cookie);

        RestAssured.given().port(port).log().all()
                .cookie(cookie)
                .when().get("/reservations/my")
                .then().statusCode(200)
                .body("size()", is(1));
    }

    private Cookie loginForTest() {
        return RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(Map.of("email", "may@gmail.com", "password", "1234"))
                .when().post("/auth/login")
                .then().extract().detailedCookie("token");
    }

    private void createReservationForTest(Cookie cookie) {
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .cookie(cookie)
                .body(Map.of("seatRank", "TABLE", "seatNumber", 1, "date", LocalDate.of(2025, 5, 5)))
                .when().post("/reservations");
    }

    @DisplayName("/reservations/seat PATCH 예약 좌석 변경 테스트")
    @Test
    void patch1() {
        Cookie cookie = loginForTest();
        createReservationForTest(cookie);

        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .cookie(cookie)
                .body(Map.of("id", 1L, "seatNumber", 2))
                .when().patch("/reservations/seat")
                .then().statusCode(200);
    }
}
