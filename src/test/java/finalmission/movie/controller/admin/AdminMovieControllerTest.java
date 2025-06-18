package finalmission.movie.controller.admin;

import static org.hamcrest.Matchers.equalTo;

import finalmission.movie.dto.request.MovieCreateRequest;
import finalmission.movie.dto.request.MovieSlotCreateRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdminMovieControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("관리자 영화 생성 - 성공")
    void createMovie() {
        // given
        MovieCreateRequest request = new MovieCreateRequest("영화 이름", "영화 설명");

        // when & then
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/admin/movies")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("영화 이름"));
    }

    @Test
    @DisplayName("관리자 영화 슬롯 생성 - 성공")
    void createMovieSlot() {
        // given
        MovieCreateRequest movieCreateRequest = new MovieCreateRequest("영화 이름", "영화 설명");
        Long movieId = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(movieCreateRequest)
                .when()
                .post("/admin/movies")
                .then()
                .extract()
                .response()
                .jsonPath()
                .getLong("id");
        Integer seats = 200;

        MovieSlotCreateRequest movieSlotCreateRequest = new MovieSlotCreateRequest(
                movieId, LocalDate.now(), LocalTime.now(), seats);

        // when & then
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(movieSlotCreateRequest)
                .when()
                .post("/admin/movie-slots")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("movieName", equalTo("영화 이름"));
    }
}
