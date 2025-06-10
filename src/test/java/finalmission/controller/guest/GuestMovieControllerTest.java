package finalmission.controller.guest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import finalmission.entity.Movie;
import finalmission.entity.MovieSlot;
import finalmission.fixture.MovieFixture;
import finalmission.fixture.MovieSlotFixture;
import finalmission.repository.MovieRepository;
import finalmission.repository.MovieSlotRepository;
import io.restassured.RestAssured;
import java.util.List;
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
class GuestMovieControllerTest {

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
    @DisplayName("손님 영화 조회 - 성공")
    void readMovieSlot() {
        // given
        Movie movie = MovieFixture.createDefault();
        movieRepository.save(movie);

        MovieSlot movieSlot = MovieSlotFixture.create(movie);
        movieSlotRepository.save(movieSlot);

        // when & then
        RestAssured
                .given()
                .when()
                .get("/movies")
                .then()
                .body("$", hasSize(1))
                .body("[0].movieName", equalTo(movie.getName()))
                .body("[0].totalSeats", equalTo(movieSlot.getSeats()));
    }

    @Test
    @DisplayName("손님 영화 조회 - 성공 - 필터링")
    void readMovieSlotFiltering() {
        // given
        List<Movie> movies = MovieFixture.createDefaultList(3);
        movieRepository.saveAll(movies);

        List<MovieSlot> movieSlots = MovieSlotFixture.createList(movies, movies.size());
        movieSlotRepository.saveAll(movieSlots);

        // when & then
        RestAssured
                .given()
                .when()
                .queryParam("movieId", movies.getFirst().getId())
                .queryParam("date", movieSlots.getFirst().getDate().toString())
                .get("/movies")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(1))
                .body("[0].movieName", equalTo(movies.getFirst().getName()))
                .body("[0].totalSeats", equalTo(movieSlots.getFirst().getSeats()));
    }
}
