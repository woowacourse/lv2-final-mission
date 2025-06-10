package finalmission;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.dto.RestaurantSimpleResponse;
import io.restassured.RestAssured;
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
}
