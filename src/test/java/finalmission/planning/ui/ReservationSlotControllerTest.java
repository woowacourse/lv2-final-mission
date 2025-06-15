package finalmission.planning.ui;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.planning.ui.dto.request.CreateReservationSlotRequest;
import finalmission.planning.ui.dto.response.ReservationSlotResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ReservationSlotControllerTest extends IntegrationTest {

    @DisplayName("예약 슬롯 추가")
    @Test
    void createReservationSlot() {
        // given
        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(12, 0);
        CreateReservationSlotRequest request = new CreateReservationSlotRequest(
                date, startTime, endTime);

        String token = saveNormalUserAndCreateToken();

        // when
        ReservationSlotResponse response = RestAssured.given().log().all()
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservationSlots")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().as(ReservationSlotResponse.class);

        // then
        SoftAssertions.assertSoftly(softly -> {
            assertThat(response.date()).isEqualTo(date);
            assertThat(response.startTime()).isEqualTo(startTime);
            assertThat(response.endTime()).isEqualTo(endTime);
        });
    }

}
