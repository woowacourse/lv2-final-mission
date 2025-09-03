package finalmission;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import finalmission.dto.request.AddWaitingRequest;
import finalmission.dto.request.ChangeStoreStatusRequest;
import finalmission.dto.request.LoginRequest;
import finalmission.dto.request.StoreCreateRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class IntegrationTest {

    @LocalServerPort
    private int port;

    private String hasWaitingCustomerAccessToken;

    private String hasNotWaitingCustomerAccessToken;

    private String hasStoreMasterAccessToken;

    private String hasNotStoreMasterAccessToken;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        LoginRequest hasWaitingCustomerLoginRequest = new LoginRequest("customer1@test.com", "password");
        LoginRequest hasNotWaitingCustomerLoginRequest = new LoginRequest("customer4@test.com", "password");
        LoginRequest hasNotStoreMasterLoginRequest = new LoginRequest("owner3@test.com", "password");
        LoginRequest hasStoreMasterLoginRequest = new LoginRequest("owner1@test.com", "password");

        Response hasWaitingcustomerResponse = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(hasWaitingCustomerLoginRequest)
                .when()
                .post("/api/v1/auth/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        hasWaitingCustomerAccessToken = hasWaitingcustomerResponse.getCookie("token");

        Response hasNotWaitingCustomerResponse = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(hasNotWaitingCustomerLoginRequest)
                .when()
                .post("/api/v1/auth/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        hasNotWaitingCustomerAccessToken = hasNotWaitingCustomerResponse.getCookie("token");

        Response hasStoreMasterResponse = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(hasStoreMasterLoginRequest)
                .when()
                .post("/api/v1/auth/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        hasStoreMasterAccessToken = hasStoreMasterResponse.getCookie("token");

        Response hasNotStoreMasterResponse = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(hasNotStoreMasterLoginRequest)
                .when()
                .post("/api/v1/auth/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response();

        hasNotStoreMasterAccessToken = hasNotStoreMasterResponse.getCookie("token");
    }

    @Test
    void 가게를_생성한다() {
        // given
        StoreCreateRequest storeCreateRequest = new StoreCreateRequest("storeName", "description");

        // when
        ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie("token", hasNotStoreMasterAccessToken)
                .body(storeCreateRequest)
                .when()
                .post("/api/v1/stores")
                .then()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("storeName")).isEqualTo("storeName");
    }

    @Test
    void 모든_가게를_조회한다() {
        // when
        ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/v1/stores")
                .then()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("$")).hasSize(2);
        assertThat(response.jsonPath().getList("storeName"))
                .containsExactly("맛있는 식당", "분위기 좋은 카페");
    }

    @Test
    void 열려있는_모든_가게를_조회한다() {
        // when
        ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/v1/stores/open")
                .then()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("$")).hasSize(2);
        assertThat(response.jsonPath().getList("storeName"))
                .containsExactly("맛있는 식당", "분위기 좋은 카페");
    }

    @Test
    void 가게의_상태를_변경한다() {
        //given
        ChangeStoreStatusRequest changeStoreStatusRequest = new ChangeStoreStatusRequest("CLOSED");

        // when
        ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie("token", hasStoreMasterAccessToken)
                .body(changeStoreStatusRequest)
                .when()
                .patch("/api/v1/stores/1/status")
                .then()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 대기를_추가한다() {
        // given
        AddWaitingRequest addWaitingRequest = new AddWaitingRequest(1L);

        // when
        ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie("token", hasNotWaitingCustomerAccessToken)
                .body(addWaitingRequest)
                .when()
                .post("/api/v1/stores/waiting")
                .then()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getInt("rank")).isEqualTo(3);
    }

    @Test
    void 대기를_삭제한다() {
        // when
        ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie("token", hasNotWaitingCustomerAccessToken)
                .when()
                .delete("/api/v1/stores/1")
                .then()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 대기순서를_얻는다() {
        // when
        ExtractableResponse<Response> response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .cookie("token", hasWaitingCustomerAccessToken)
                .when()
                .get("/api/v1/stores/1/waiting/rank")
                .then()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getInt("rank")).isEqualTo(1);
    }
}
