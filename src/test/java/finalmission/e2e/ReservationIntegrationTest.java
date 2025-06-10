package finalmission.e2e;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.reservation.dto.MyReservationResponse;
import finalmission.reservation.dto.ReservationCreateRequest;
import finalmission.reservation.dto.ReservationCreateResponse;
import finalmission.reservation.dto.ReservationDeleteRequest;
import finalmission.reservation.dto.ReservationModifyRequest;
import finalmission.reservation.dto.ReservationModifyResponse;
import finalmission.reservation.dto.ReservationSearchRequest;
import finalmission.reservation.dto.SearchReservationResponse;
import io.restassured.RestAssured;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {
        "spring.sql.init.data-locations=classpath:test-data.sql"
})
public class ReservationIntegrationTest {

    public static final String TOKEN = "token";
    private static final LocalDateTime TIME = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MINUTES);
    private static final String DESCRIPTION = "description";
    private static final long ROOM_ID = 1L;

    @LocalServerPort
    private int port;

    private String regularToken;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        regularToken = TestFixture.loginRegular();
    }

    @Test
    void createReservation() {
        ReservationCreateResponse reservationCreateResponse = RestAssured.given().log().all()
                .cookie(TOKEN, regularToken)
                .body(new ReservationCreateRequest(TIME, DESCRIPTION, ROOM_ID))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .extract()
                .as(ReservationCreateResponse.class);
        assertThat(reservationCreateResponse).isNotNull();
    }

    @Test
    void searchReservations() {
        TestFixture.makeReservation(TIME, DESCRIPTION, ROOM_ID, regularToken);

        SearchReservationResponse response = RestAssured.given().log().all()
                .cookie(TOKEN, regularToken)
                .body(new ReservationSearchRequest(TIME.toLocalDate(), ROOM_ID))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/search")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(SearchReservationResponse.class);
        assertThat(response.responses().size()).isOne();
    }

    @Test
    void findMyReservations() {
        TestFixture.makeReservation(TIME, DESCRIPTION, ROOM_ID, regularToken);

        MyReservationResponse response = RestAssured.given().log().all()
                .cookie(TOKEN, regularToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/mine")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MyReservationResponse.class);
        assertThat(response.responses().size()).isOne();
    }

    @Test
    void modifyReservation() {
        Long reservationId = TestFixture.makeReservation(TIME, DESCRIPTION, ROOM_ID, regularToken);

        LocalDateTime modifyTime = LocalDateTime.now().plusDays(1).plusHours(1);

        ReservationModifyResponse response = RestAssured.given().log().all()
                .cookie(TOKEN, regularToken)
                .body(new ReservationModifyRequest(reservationId, modifyTime, DESCRIPTION, ROOM_ID))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/reservations")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(ReservationModifyResponse.class);
        assertThat(response.reservationTime()).isEqualTo(modifyTime.truncatedTo(ChronoUnit.MINUTES));
    }


    @Test
    void deleteReservation() {
        Long reservationId = TestFixture.makeReservation(TIME, DESCRIPTION, ROOM_ID, regularToken);

        RestAssured.given().log().all()
                .cookie(TOKEN, regularToken)
                .body(new ReservationDeleteRequest(reservationId))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations")
                .then().log().all()
                .statusCode(204);

        MyReservationResponse myReservationResponse = RestAssured.given().log().all()
                .cookie(TOKEN, regularToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations/mine")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(MyReservationResponse.class);
        assertThat(myReservationResponse.responses().size()).isZero();
    }
}
