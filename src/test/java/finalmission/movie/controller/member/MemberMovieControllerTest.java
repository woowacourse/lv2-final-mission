package finalmission.movie.controller.member;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import finalmission.movie.dto.request.MovieReservationCreateRequest;
import finalmission.movie.entity.Movie;
import finalmission.movie.entity.MovieSlot;
import finalmission.fixture.MovieFixture;
import finalmission.fixture.MovieSlotFixture;
import finalmission.movie.repository.MovieRepository;
import finalmission.movie.repository.MovieSlotRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MemberMovieControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieSlotRepository movieSlotRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("회원 영화 예약 - 성공")
    void createMovieReservation() {
        // given
        Movie movie = MovieFixture.createDefault();
        movieRepository.save(movie);

        MovieSlot movieSlot = MovieSlotFixture.create(movie);
        movieSlotRepository.save(movieSlot);

        Integer selectSeat = 1;
        MovieReservationCreateRequest request = new MovieReservationCreateRequest(
                "회원 이름",
                movieSlot.getId(),
                selectSeat
        );

        // when & then
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("movies/reservation")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("memberName", equalTo("회원 이름"))
                .body("movieName", equalTo(movie.getName()));
    }

    @Test
    @DisplayName("회원 예약 조회 - 성공")
    void readMovieReservation() {
        // given
        Movie movie = MovieFixture.createDefault();
        movieRepository.save(movie);

        MovieSlot movieSlot = MovieSlotFixture.create(movie);
        movieSlotRepository.save(movieSlot);

        Integer selectSeat = 1;
        MovieReservationCreateRequest request = new MovieReservationCreateRequest(
                "회원 이름",
                movieSlot.getId(),
                selectSeat
        );
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("movies/reservation");

        // when & then
        RestAssured
                .given()
                .when()
                .queryParam("memberName", "회원 이름")
                .get("/movies/reservation")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(1))
                .body("[0].movieName", equalTo(movie.getName()))
                .body("[0].seat", equalTo(selectSeat));
    }
}
