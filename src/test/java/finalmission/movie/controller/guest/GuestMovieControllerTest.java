package finalmission.movie.controller.guest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import finalmission.fixture.MemberFixture;
import finalmission.fixture.MovieFixture;
import finalmission.fixture.MovieSlotFixture;
import finalmission.holiday.service.HolidayCheckService;
import finalmission.member.entity.Member;
import finalmission.member.repository.MemberRepository;
import finalmission.movie.entity.Movie;
import finalmission.movie.entity.MovieReservation;
import finalmission.movie.entity.MovieSlot;
import finalmission.movie.repository.MovieRepository;
import finalmission.movie.repository.MovieReservationRepository;
import finalmission.movie.repository.MovieSlotRepository;
import io.restassured.RestAssured;
import java.time.LocalDate;
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
import org.springframework.test.context.bean.override.mockito.MockitoBean;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GuestMovieControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieSlotRepository movieSlotRepository;
    @Autowired
    private MovieReservationRepository movieReservationRepository;
    @Autowired
    private MemberRepository memberRepository;
    @MockitoBean
    private HolidayCheckService holidayCheckService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        given(holidayCheckService.isHoliday(any()))
                .willReturn(false);
        given(holidayCheckService.isHoliday(LocalDate.of(2025, 6, 6)))
                .willReturn(true);
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
    @DisplayName("손님 영화 조회 - 성공 - 기본 가격")
    void readMovieSlot_success_normalPrice() {
        // given
        Movie movie = MovieFixture.createDefault();
        movieRepository.save(movie);

        MovieSlot movieSlot = MovieSlotFixture.create(movie, LocalDate.of(2025, 6, 5));
        movieSlotRepository.save(movieSlot);

        // when & then
        RestAssured
                .given()
                .queryParam("date", "2025-06-05")
                .when()
                .get("/movies")
                .then()
                .body("$", hasSize(1))
                .body("[0].movieName", equalTo(movie.getName()))
                .body("[0].totalSeats", equalTo(movieSlot.getSeats()))
                .body("[0].price", equalTo(10000));
    }

    @Test
    @DisplayName("손님 영화 조회 - 성공 - 주말 가격")
    void readMovieSlot_success_holidayPrice() {
        // given
        Movie movie = MovieFixture.createDefault();
        movieRepository.save(movie);

        MovieSlot movieSlot = MovieSlotFixture.create(movie, LocalDate.of(2025, 6, 6));
        movieSlotRepository.save(movieSlot);

        // when & then
        RestAssured
                .given()
                .queryParam("date", "2025-06-06")
                .when()
                .get("/movies")
                .then()
                .body("$", hasSize(1))
                .body("[0].movieName", equalTo(movie.getName()))
                .body("[0].totalSeats", equalTo(movieSlot.getSeats()))
                .body("[0].price", equalTo(15000));
    }

    @Test
    @DisplayName("손님 영화 조회 - 성공 - 남은 좌석 확인")
    void readMovieSlotCheckLeftSeats() {
        // given
        Movie movie = MovieFixture.createDefault();
        movieRepository.save(movie);

        MovieSlot movieSlot = MovieSlotFixture.create(movie);
        movieSlotRepository.save(movieSlot);

        Member member = MemberFixture.createDefault();
        memberRepository.save(member);

        Integer selectSeat = 1;
        MovieReservation movieReservation = new MovieReservation(member, movieSlot, selectSeat);
        movieReservationRepository.save(movieReservation);

        // when & then
        Integer exceptedLeftSeats = movieSlot.getSeats() - 1;
        RestAssured
                .given()
                .when()
                .get("/movies")
                .then()
                .body("$", hasSize(1))
                .body("[0].movieName", equalTo(movie.getName()))
                .body("[0].totalSeats", equalTo(movieSlot.getSeats()))
                .body("[0].leftSeats", equalTo(exceptedLeftSeats));
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
                .body("[0].totalSeats", equalTo(movieSlots.getFirst().getSeats()))
                .body("[0].leftSeats", equalTo(movieSlots.getFirst().getSeats()));
    }
}
