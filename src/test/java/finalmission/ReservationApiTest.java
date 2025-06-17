package finalmission;


import static org.assertj.core.api.Assertions.assertThat;

import finalmission.client.RandomNicknameGenerator;
import finalmission.domain.reservation.owner.Nickname;
import finalmission.dto.reservation.CreateReservationRequest;
import finalmission.dto.reservation.CreateReservationResponse;
import finalmission.dto.reservation.OwnerResponse;
import finalmission.dto.reservation.ReservationDetailResponse;
import finalmission.dto.schedule.ScheduleResponse;
import finalmission.fixture.DocumentationFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Sql("/test-restaurant-data.sql")
@ExtendWith(RestDocumentationExtension.class)
public class ReservationApiTest {

    @LocalServerPort
    private int port;

    private RequestSpecification documentationSpecification;

    @MockitoBean
    private RandomNicknameGenerator randomNicknameGenerator;

    @BeforeEach
    void setUp(final RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;
        this.documentationSpecification = DocumentationFixture
                .createDefaultDocumentationSpecification(restDocumentation);
    }

    @DisplayName("POST /schedules/{id}/reservations : 맛집 예약 생성")
    @Test
    void createReservation() {
        // given
        CreateReservationRequest request = new CreateReservationRequest(
                "norang@gmail.com",
                "1234",
                5,
                "5명 예약이요"
        );
        CreateReservationResponse expectedResponse = new CreateReservationResponse(
                3L,
                new OwnerResponse("랜덤닉네임", "norang@gmail.com", "1234"),
                new ReservationDetailResponse(5, "5명 예약이요"),
                new ScheduleResponse(1L, LocalDate.of(2025, 6, 21), LocalTime.of(13, 0), 0)
        );
        // when
        Mockito.when(randomNicknameGenerator.generate()).thenReturn(new Nickname("랜덤닉네임"));
        CreateReservationResponse actualResponse = RestAssured.given(documentationSpecification).log().all()
                .body(request)
                .contentType(ContentType.JSON)
                .filter(DocumentationFixture.createDocumentWithDefaultPath())
                .when().post("/schedules/{id}/reservations", 1L)
                .then().log().all()        // then
                .statusCode(201)
                .extract().as(CreateReservationResponse.class);
        // then
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}
