package finalmission.cake.controller;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = "cakecontrollerdata.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
@Transactional
class CakeControllerTest {

    @LocalServerPort
    int port;

    private String userCookie;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        Map<String, Object> loginParam = new HashMap<>();
        loginParam.put("email", "user@email.com");
        loginParam.put("password", "1234");

        String cookieHeader = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginParam)
                .when().post("/login")
                .then().log().all().extract().header("Set-Cookie").split(";")[0];

         userCookie = cookieHeader.substring("authorization=".length());
    }

    @Test
    void findAllCakeTest() {
        Response response = RestAssured.given().log().all()
                .when().get("/cakes")
                .then()
                .statusCode(200)
                .extract().response();

        List<Map<String, Object>> cakes = response.jsonPath().getList("$");
        List<String> cakeNames = cakes.stream()
                .map(cake -> (String) cake.get("name"))
                .toList();

        assertThat(cakeNames).containsExactlyInAnyOrder("하트케이크", "캐릭터케이크", "떡케이크");
        assertThat(cakes).hasSize(3);
    }

    @Test
    void findCakeDetailsTest() {
        Response response = RestAssured.given().log().all()
                .when().get("/cakes/detail")
                .then()
                .statusCode(200)
                .extract().response();

        List<Map<String, Object>> flavors = response.jsonPath().getList("flavors");
        List<Map<String, Object>> sizes = response.jsonPath().getList("size");

        assertThat(flavors).hasSize(4);
        assertThat(sizes).hasSize(4);
    }

    @Test
    void findAvailableTimeByDateTest() {
        Response response = RestAssured.given().log().all()
                .param("date", "2025.06.21")
                .when().get("/cakes/1")
                .then()
                .statusCode(200)
                .extract().response();

        List<Map<String, Object>> times = response.jsonPath().getList("$");

        assertThat(times).isNotEmpty();
        assertThat(times.get(0).get("isAvailable")).isEqualTo(false);
    }

    @Test
    void getMemberCakeReservationsTest() {
        Response response = RestAssured.given().log().all()
                .cookie("authorization", userCookie)
                .when().get("/members/cakes")
                .then()
                .statusCode(200)
                .extract().response();

        List<Map<String, Object>> reservations = response.jsonPath().getList("$");
        assertThat(reservations).isNotNull();
    }

    @Test
    void createCakeReservationTest() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("date", "2025.06.30");
        requestBody.put("cakeId", 1);
        requestBody.put("flavorId", 1);
        requestBody.put("sizeId", 1);
        requestBody.put("timeId", 2);
        requestBody.put("lettering", "축하해");

        Response response = RestAssured.given().log().all()
                .cookie("authorization", userCookie)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/cakes")
                .then()
                .statusCode(201)
                .extract().response();

        assertThat(response.jsonPath().getLong("reservationId")).isNotNull();
    }

    @Test
    void deleteCakeReservationTest() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("date", "2025.06.30");
        requestBody.put("cakeId", 1);
        requestBody.put("flavorId", 1);
        requestBody.put("sizeId", 1);
        requestBody.put("timeId", 2);
        requestBody.put("lettering", "삭제용");

        Long reservationId = RestAssured.given()
                .cookie("authorization", userCookie)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/cakes")
                .then()
                .statusCode(201)
                .extract().jsonPath().getLong("reservationId");

        RestAssured.given().log().all()
                .cookie("authorization", userCookie)
                .when().delete("/cakes/" + reservationId)
                .then()
                .statusCode(200);
    }

    @Test
    void updateCakeReservationTest() {
        Map<String, Object> params = new HashMap<>();
        params.put("date", "2025.06.21");
        params.put("cakeId", 1);
        params.put("flavorId", 1);
        params.put("sizeId", 1);
        params.put("timeId", 1);
        params.put("lettering", "수정문구");

        Response response = RestAssured.given().log().all()
                .cookie("authorization", userCookie)
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/cakes/1")
                .then()
                .statusCode(200)
                .extract().response();

        assertThat(response.jsonPath().getString("lettering")).isEqualTo("수정문구");
    }
}