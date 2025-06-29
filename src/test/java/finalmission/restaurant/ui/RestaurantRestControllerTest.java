package finalmission.restaurant.ui;

import static finalmission.fixture.LoginApiFixture.adminLoginAndGetCookies;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import finalmission.restaurant.ui.dto.CreateRestaurantRequest;
import finalmission.restaurant.ui.dto.RestaurantResponse;
import io.restassured.http.ContentType;
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
class RestaurantRestControllerTest {

    @Test
    void 음식점을_추가한다() {
        final String restaurantName = "차이나 스토리";
        final String restaurantDescription = "역시 차스입니다.";
        final String restaurantPlace = "서울시 어딘가1";
        final String restaurantPhoneNumber = "02-1234-5678";
        final int maxReservationCount = 20;

        final Map<String, String> adminCookies = adminLoginAndGetCookies();
        final CreateRestaurantRequest request = new CreateRestaurantRequest(
                restaurantName,
                restaurantDescription,
                restaurantPlace,
                restaurantPhoneNumber,
                maxReservationCount
        );

        final RestaurantResponse response = given().log().all()
                .cookies(adminCookies)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/restaurants")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(RestaurantResponse.class);

        assertThat(response).isNotNull();
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.id()).isNotNull();
            softAssertions.assertThat(response.name()).isEqualTo(restaurantName);
            softAssertions.assertThat(response.description()).isEqualTo(restaurantDescription);
            softAssertions.assertThat(response.place()).isEqualTo(restaurantPlace);
            softAssertions.assertThat(response.phoneNumber()).isEqualTo(restaurantPhoneNumber);
        });
    }

    @Test
    void 음식점_목록을_조회한다() {
        // 음식점 생성
        final String firstRestaurantName = "첫 번째 음식점";
        final String firstRestaurantDescription = "첫 번째 음식점 설명";
        final String firstRestaurantPlace = "서울시 강남구";
        final String firstRestaurantPhoneNumber = "02-1111-1111";
        final int firstMaxReservationCount = 11;

        final String secondRestaurantName = "두 번째 음식점";
        final String secondRestaurantDescription = "두 번째 음식점 설명";
        final String secondRestaurantPlace = "서울시 서초구";
        final String secondRestaurantPhoneNumber = "02-2222-2222";
        final int secondMaxReservationCount = 22;

        final Map<String, String> adminCookies = adminLoginAndGetCookies();

        final CreateRestaurantRequest request1 = new CreateRestaurantRequest(
                firstRestaurantName,
                firstRestaurantDescription,
                firstRestaurantPlace,
                firstRestaurantPhoneNumber,
                firstMaxReservationCount
        );

        final CreateRestaurantRequest request2 = new CreateRestaurantRequest(
                secondRestaurantName,
                secondRestaurantDescription,
                secondRestaurantPlace,
                secondRestaurantPhoneNumber,
                secondMaxReservationCount
        );

        given().log().all()
                .cookies(adminCookies)
                .contentType(ContentType.JSON)
                .body(request1)
                .when()
                .post("/restaurants")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        given().log().all()
                .cookies(adminCookies)
                .contentType(ContentType.JSON)
                .body(request2)
                .when()
                .post("/restaurants")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        // 음식점 목록 조회
        final List<RestaurantResponse> responses = given().log().all()
                .when()
                .get("/restaurants")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", RestaurantResponse.class);

        // 검증
        assertThat(responses).hasSize(2);
        final RestaurantResponse firstRestaurant = responses.stream()
                .filter(response -> response.name().equals(firstRestaurantName))
                .findFirst()
                .orElse(null);
        final RestaurantResponse secondRestaurant = responses.stream()
                .filter(response -> response.name().equals(secondRestaurantName))
                .findFirst()
                .orElse(null);

        assertThat(firstRestaurant).isNotNull();
        assertThat(secondRestaurant).isNotNull();
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(firstRestaurant.name()).isEqualTo(firstRestaurantName);
            softAssertions.assertThat(firstRestaurant.description()).isEqualTo(firstRestaurantDescription);
            softAssertions.assertThat(firstRestaurant.place()).isEqualTo(firstRestaurantPlace);
            softAssertions.assertThat(firstRestaurant.phoneNumber()).isEqualTo(firstRestaurantPhoneNumber);

            softAssertions.assertThat(secondRestaurant.name()).isEqualTo(secondRestaurantName);
            softAssertions.assertThat(secondRestaurant.description()).isEqualTo(secondRestaurantDescription);
            softAssertions.assertThat(secondRestaurant.place()).isEqualTo(secondRestaurantPlace);
            softAssertions.assertThat(secondRestaurant.phoneNumber()).isEqualTo(secondRestaurantPhoneNumber);
        });
    }

    @Test
    void 음식점을_삭제한다() {
        // 음식점 추가
        final String restaurantName = "삭제할 음식점";
        final String restaurantDescription = "삭제될 음식점 설명";
        final String restaurantPlace = "서울시 마포구";
        final String restaurantPhoneNumber = "02-3333-3333";
        final int maxReservationCount = 30;

        final Map<String, String> adminCookies = adminLoginAndGetCookies();

        final CreateRestaurantRequest request = new CreateRestaurantRequest(
                restaurantName,
                restaurantDescription,
                restaurantPlace,
                restaurantPhoneNumber,
                maxReservationCount
        );

        final RestaurantResponse createdRestaurant = given().log().all()
                .cookies(adminCookies)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/restaurants")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(RestaurantResponse.class);

        assertThat(createdRestaurant).isNotNull();
        assertThat(createdRestaurant.id()).isNotNull();

        // 음식점 삭제
        given().log().all()
                .cookies(adminCookies)
                .when()
                .delete("/restaurants/" + createdRestaurant.id())
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // 삭제 검증
        final List<RestaurantResponse> responses = given().log().all()
                .when()
                .get("/restaurants")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", RestaurantResponse.class);

        assertThat(responses).isEmpty();
    }
}