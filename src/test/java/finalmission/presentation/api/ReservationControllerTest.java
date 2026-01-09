package finalmission.presentation.api;

import static org.hamcrest.Matchers.equalTo;

import finalmission.auth.AuthToken;
import finalmission.auth.jwt.JJWTJwtUtil;
import finalmission.business.model.entity.Member;
import finalmission.business.service.ReservationService;
import finalmission.infrastructure.repository.ReservationRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("RestAssured 테스트")
class ReservationControllerTest {
    private static final String INCHEON = "인천국제공항";
    private static final String OSAKA = "간사이국제공항";
    private static final LocalDateTime TOMORROW = LocalDateTime.now().plusDays(1);
    private final String TOKEN_NAME = "auth_token";
    @Autowired
    JJWTJwtUtil jjwtJwtUtil;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationService reservationService;
    private AuthToken authToken;
    private Member ddiyong;

    void setAuthToken() {
        ddiyong = Member.create(1L, "띠용", "ddiyong@gmail.com", "1234");
        authToken = jjwtJwtUtil.createToken(ddiyong);
    }

    @Test
    @DisplayName("내 예약 전체 조회 테스트")
    void test1() {
        // given
        setAuthToken();
        // when & then
        RestAssured.given().log().all()
                .cookie(TOKEN_NAME, authToken.value())
                .contentType(ContentType.JSON)
                .when()
                .get("/reservations")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("특정 예약 조회 테스트")
    void test3() {
        // given
        Long testId = 1L;
        setAuthToken();
        // when & then
        RestAssured.given().log().all()
                .pathParam("id", testId)
                .cookie(TOKEN_NAME, authToken.value())
                .contentType(ContentType.JSON)
                .when()
                .get("/reservations/{id}")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("예약 수정 테스트")
    void test2() {
        // given
        Long testId = 1L;
        setAuthToken();
        String fixedPassPortId = "fixedPassPortId";

        // when & then
        RestAssured.given().log().all()
                .pathParam("id", testId)
                .cookie(TOKEN_NAME, authToken.value())
                .contentType(ContentType.JSON)
                .when()
                .body(fixedPassPortId)
                .post("/reservations/{id}")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("passportId", equalTo(fixedPassPortId));
    }

    @Test
    @DisplayName("예약 삭제 테스트")
    void test() {
        // given
        Long testId = 1L;
        setAuthToken();

        // when & then
        RestAssured.given().log().all()
                .pathParam("id", testId)
                .cookie(TOKEN_NAME, authToken.value())
                .contentType(ContentType.JSON)
                .when()
                .delete("/reservations/{id}")
                .then().log().all()
                .assertThat()
                .statusCode(204);

        RestAssured.given().log().all()
                .pathParam("id", testId)
                .cookie(TOKEN_NAME, authToken.value())
                .contentType(ContentType.JSON)
                .when()
                .get("/reservations/{id}")
                .then().log().all()
                .assertThat()
                .statusCode(400);
    }

}
