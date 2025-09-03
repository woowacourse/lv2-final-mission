package finalmission.movie.controller.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import finalmission.fixture.MemberFixture;
import finalmission.fixture.MovieFixture;
import finalmission.fixture.MovieSlotFixture;
import finalmission.helper.RestAssureHelper;
import finalmission.member.entity.Member;
import finalmission.member.repository.MemberRepository;
import finalmission.movie.dto.request.MovieReservationCreateRequest;
import finalmission.movie.entity.Movie;
import finalmission.movie.entity.MovieReservation;
import finalmission.movie.entity.MovieSlot;
import finalmission.movie.repository.MovieRepository;
import finalmission.movie.repository.MovieReservationRepository;
import finalmission.movie.repository.MovieSlotRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
class MemberMovieControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieSlotRepository movieSlotRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MovieReservationRepository movieReservationRepository;

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

        Member member = MemberFixture.createDefault();
        memberRepository.save(member);
        Integer selectSeat = 1;
        MovieReservationCreateRequest request = new MovieReservationCreateRequest(movieSlot.getId(), selectSeat);
        String token = RestAssureHelper.getLoginToken(member.getEmail(), member.getPassword());

        // when & then
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)

                .when()
                .post("movies/reservation")

                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("memberName", equalTo(member.getName()))
                .body("movieName", equalTo(movie.getName()));
    }

    @Test
    @DisplayName("회원 영화 예약 - 실패 - 이미 예약됨")
    void createMovieReservation_fail_alreadyBooked() {
        // given
        Movie movie = MovieFixture.createDefault();
        movieRepository.save(movie);

        MovieSlot movieSlot = MovieSlotFixture.create(movie);
        movieSlotRepository.save(movieSlot);

        Member member = MemberFixture.createDefault();
        memberRepository.save(member);
        Integer selectSeat = 1;
        MovieReservationCreateRequest request = new MovieReservationCreateRequest(movieSlot.getId(), selectSeat);
        String token = RestAssureHelper.getLoginToken(member.getEmail(), member.getPassword());

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)

                .when()
                .post("movies/reservation");

        // when & then
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)

                .when()
                .post("movies/reservation")

                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }
    
    @Test
    @DisplayName("회원 예약 조회 - 성공")
    void readMovieReservation() {
        // given
        Movie movie = MovieFixture.createDefault();
        movieRepository.save(movie);

        MovieSlot movieSlot = MovieSlotFixture.create(movie);
        movieSlotRepository.save(movieSlot);

        Member member = MemberFixture.createDefault();
        memberRepository.save(member);
        Integer selectSeat = 1;
        MovieReservationCreateRequest request = new MovieReservationCreateRequest(movieSlot.getId(), selectSeat);
        String token = RestAssureHelper.getLoginToken(member.getEmail(), member.getPassword());

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)

                .when()
                .post("movies/reservation");

        // when & then
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .cookie("token", token)

                .when()
                .get("/movies/reservation")

                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$", hasSize(1))
                .body("[0].movieName", equalTo(movie.getName()))
                .body("[0].seat", equalTo(selectSeat));
    }

    @Test
    @DisplayName("회원 영화 예약 삭제 - 성공")
    void deleteMovieReservation() {
        // given
        Movie movie = MovieFixture.createDefault();
        movieRepository.save(movie);

        MovieSlot movieSlot = MovieSlotFixture.create(movie);
        movieSlotRepository.save(movieSlot);

        Member member = MemberFixture.createDefault();
        memberRepository.save(member);
        Integer selectSeat = 1;
        MovieReservationCreateRequest request = new MovieReservationCreateRequest(movieSlot.getId(), selectSeat);
        String token = RestAssureHelper.getLoginToken(member.getEmail(), member.getPassword());

        Integer movieReservationId = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)

                .when()
                .post("/movies/reservation")

                .then()
                .extract().jsonPath()
                .get("id");

        // when & then
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .cookie("token", token)

                .when()
                .delete("/movies/reservation/{id}", movieReservationId);

        List<MovieReservation> result = movieReservationRepository.findAll();
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("회원 영화 예약 삭제 - 실패 - 다른 회원 요청")
    void deleteMovieReservation_fail_otherMemberRequest() {
        // given
        Movie movie = MovieFixture.createDefault();
        movieRepository.save(movie);

        MovieSlot movieSlot = MovieSlotFixture.create(movie);
        movieSlotRepository.save(movieSlot);

        Member member = MemberFixture.createDefault();
        memberRepository.save(member);
        Integer selectSeat = 1;
        MovieReservationCreateRequest request = new MovieReservationCreateRequest(movieSlot.getId(), selectSeat);
        String token = RestAssureHelper.getLoginToken(member.getEmail(), member.getPassword());

        Integer movieReservationId = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(request)

                .when()
                .post("/movies/reservation")

                .then()
                .extract().jsonPath()
                .get("id");

        Member otherMember = MemberFixture.createDefault();
        memberRepository.save(otherMember);
        String otherMemberToken = RestAssureHelper.getLoginToken(otherMember.getEmail(), otherMember.getPassword());

        // when & then
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .cookie("token", otherMemberToken)

                .when()
                .delete("/movies/reservation/{id}", movieReservationId)

                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }
}
