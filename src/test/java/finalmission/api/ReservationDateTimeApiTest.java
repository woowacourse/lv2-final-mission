package finalmission.api;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.domain.ReservationDateTime;
import finalmission.dto.response.ReservationDateTimeResponse;
import finalmission.infrastructure.ReservationDateTimeRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationDateTimeApiTest {

    @Autowired
    private ReservationDateTimeRepository reservationDateTimeRepository;

    @Test
    void 모든_예약날짜_시간_조회() {

        //given
        ReservationDateTime reservationDateTime1 = new ReservationDateTime(null, LocalDate.of(2025, 5, 5),
                LocalTime.of(23, 0));
        ReservationDateTime reservationDateTime2 = new ReservationDateTime(null, LocalDate.of(2025, 5, 6),
                LocalTime.of(23, 0));
        ReservationDateTime reservationDateTime3 = new ReservationDateTime(null, LocalDate.of(2025, 5, 7),
                LocalTime.of(23, 0));

        reservationDateTimeRepository.save(reservationDateTime1);
        reservationDateTimeRepository.save(reservationDateTime2);
        reservationDateTimeRepository.save(reservationDateTime3);

        //when
        List<ReservationDateTimeResponse> response = RestAssured.given().log().all()
                .when().get("/api/datetimes")
                .then().log().all().statusCode(200)
                .extract().jsonPath().getList(".", ReservationDateTimeResponse.class);

        //then
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(response).hasSize(3);
        soft.assertThat(response.getFirst().startAt()).isEqualTo(LocalTime.of(23, 0));
        soft.assertAll();
    }

    @Test
    void 예약시간_생성() {
        //given
        Map<String, String> reservationDateTimeRequest = new HashMap<>();
        reservationDateTimeRequest.put("date", "2025-05-05");
        reservationDateTimeRequest.put("startAt", "10:00");

        //when
        ReservationDateTimeResponse response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationDateTimeRequest)
                .when().post("api/datetimes")
                .then().log().all()
                .statusCode(201)
                .extract().as(ReservationDateTimeResponse.class);

        //then
        List<ReservationDateTime> allDateTimes = reservationDateTimeRepository.findAll();
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(response.startAt()).isEqualTo(LocalTime.of(10, 0));
        soft.assertThat(response.date()).isEqualTo(LocalDate.of(2025, 5, 5));
        soft.assertThat(allDateTimes).hasSize(1);
        soft.assertAll();
    }

    @Test
    void 예약시간_삭제() {
        //given
        ReservationDateTime reservationDateTime1 = new ReservationDateTime(null, LocalDate.of(2025, 5, 5),
                LocalTime.of(23, 0));
        reservationDateTimeRepository.save(reservationDateTime1);

        //when
        RestAssured.given().log().all()
                .when().delete("api/datetimes/{datetimesId}", 1L)
                .then().log().all()
                .statusCode(204);

        //then
        List<ReservationDateTime> allDateTimes = reservationDateTimeRepository.findAll();
        assertThat(allDateTimes).hasSize(0);
    }

    @Test
    void 없는_예약시간_삭제() {
        //given
        ReservationDateTime reservationDateTime1 = new ReservationDateTime(null, LocalDate.of(2025, 5, 5),
                LocalTime.of(23, 0));
        reservationDateTimeRepository.save(reservationDateTime1);

        //when & then
        RestAssured.given().log().all()
                .when().delete("api/datetimes/{datetimesId}", 2L)
                .then().log().all()
                .statusCode(404);
    }
}
