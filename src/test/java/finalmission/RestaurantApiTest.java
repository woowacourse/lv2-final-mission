package finalmission;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.dto.RestaurantDetailResponse;
import finalmission.dto.RestaurantSimpleResponse;
import finalmission.dto.ScheduleResponse;
import io.restassured.RestAssured;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Sql("/test-restaurant-data.sql")
public class RestaurantApiTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("GET /restaurants : 맛집 리스트 조회 API")
    @Test
    void findAllRestaurants() {
        // given
        // when
        RestaurantSimpleResponse[] actualResponse = RestAssured.given().log().all()
                .when().get("/restaurants")
                .then().log().all()
                .statusCode(200)
                .extract().as(RestaurantSimpleResponse[].class);
        // then
        assertThat(actualResponse).hasSize(3);
    }

    @DisplayName("GET /restaurants/{id} : 맛집 상세 정보 조회 API")
    @Test
    void findRestaurantById() {
        // given
        RestaurantDetailResponse expectedResponse = new RestaurantDetailResponse(1L, "식당1", "인계동", "아주 맛있는 식당",
                List.of(
                        new ScheduleResponse(1L, LocalDate.of(2025, 6, 11), LocalTime.of(13, 0), 5),
                        new ScheduleResponse(2L, LocalDate.of(2025, 6, 11), LocalTime.of(14, 0), 20)
                )
        );
        // when
        RestaurantDetailResponse actualResponse = RestAssured.given().log().all()
                .when().get("/restaurants/{id}", 1L)
                .then().log().all()
                .statusCode(200)
                .extract().as(RestaurantDetailResponse.class);
        // then
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}
