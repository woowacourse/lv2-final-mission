package finalmission.reservation.ui;

import static finalmission.fixture.LoginApiFixture.adminLoginAndGetCookies;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import finalmission.reservation.ui.dto.CreateReservationTimeRequest;
import finalmission.reservation.ui.dto.ReservationTimeResponse;
import io.restassured.http.ContentType;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayNameGeneration(ReplaceUnderscores.class)
class ReservationTimeRestControllerTest {

    @Test
    void 예약_시간을_추가한다() {
        final LocalTime startAt = LocalTime.of(12, 0);

        final Map<String, String> adminCookies = adminLoginAndGetCookies();
        final CreateReservationTimeRequest request = new CreateReservationTimeRequest(startAt);

        final ReservationTimeResponse response = given().log().all()
                .cookies(adminCookies)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/times")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ReservationTimeResponse.class);

        assertThat(response).isNotNull();
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.id()).isNotNull();
            softAssertions.assertThat(response.startAt()).isEqualTo(startAt);
        });
    }

    @Test
    void 예약_시간_목록을_조회한다() {
        final LocalTime startAt1 = LocalTime.of(11, 0);
        final LocalTime startAt2 = LocalTime.of(13, 0);

        final Map<String, String> adminCookies = adminLoginAndGetCookies();

        final CreateReservationTimeRequest request1 = new CreateReservationTimeRequest(startAt1);
        final CreateReservationTimeRequest request2 = new CreateReservationTimeRequest(startAt2);

        given().log().all()
                .cookies(adminCookies)
                .contentType(ContentType.JSON)
                .body(request1)
                .when()
                .post("/times")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        given().log().all()
                .cookies(adminCookies)
                .contentType(ContentType.JSON)
                .body(request2)
                .when()
                .post("/times")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        final List<ReservationTimeResponse> responses = given().log().all()
                .when()
                .get("/times")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", ReservationTimeResponse.class);

        assertThat(responses).hasSize(2);

        final ReservationTimeResponse firstTime = responses.stream()
                .filter(response -> response.startAt().equals(startAt1))
                .findFirst()
                .orElse(null);
        final ReservationTimeResponse secondTime = responses.stream()
                .filter(response -> response.startAt().equals(startAt2))
                .findFirst()
                .orElse(null);

        assertThat(firstTime).isNotNull();
        assertThat(secondTime).isNotNull();
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(firstTime.id()).isNotNull();
            softAssertions.assertThat(firstTime.startAt()).isEqualTo(startAt1);

            softAssertions.assertThat(secondTime.id()).isNotNull();
            softAssertions.assertThat(secondTime.startAt()).isEqualTo(startAt2);
        });
    }

    @Test
    void 예약_시간을_삭제한다() {
        final LocalTime startAt = LocalTime.of(14, 0);

        final Map<String, String> adminCookies = adminLoginAndGetCookies();
        final CreateReservationTimeRequest request = new CreateReservationTimeRequest(startAt);

        final ReservationTimeResponse createdReservationTime = given().log().all()
                .cookies(adminCookies)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/times")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(ReservationTimeResponse.class);

        assertThat(createdReservationTime).isNotNull();
        assertThat(createdReservationTime.id()).isNotNull();

        given().log().all()
                .cookies(adminCookies)
                .when()
                .delete("/times/" + createdReservationTime.id())
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        final List<ReservationTimeResponse> responses = given().log().all()
                .when()
                .get("/times")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", ReservationTimeResponse.class);

        assertThat(responses).isEmpty();
    }
}