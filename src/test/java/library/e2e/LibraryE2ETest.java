package library.e2e;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import library.e2e.fixture.LibraryTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class LibraryE2ETest {

    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String USER_EMAIL = "user@example.com";
    private static final String OTHER_USER_EMAIL = "other@example.com";

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }


    @Test
    void 도서_컬렉션_조회_테스트() {
        // given
        Long bookId = 1L;

        // when & then
        RestAssured.given().log().all()
                .when().get("/book/{id}", bookId)
                .then().log().all()
                .statusCode(200)
                .body("size()", equalTo(2));
    }

    @Test
    void 도서_예약_생성_및_삭제_테스트() {
        // given
        Long collectionId = 1L;
        Map<String, String> reservationRequest = new HashMap<>();
        reservationRequest.put("email", USER_EMAIL);

        // when - 예약 생성
        LibraryTestFixture.createTestMember(USER_EMAIL);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationRequest)
                .when().post("/collection/{id}", collectionId)
                .then().log().all()
                .statusCode(200)
                .body("id", notNullValue())
                .body("email", equalTo(USER_EMAIL))
                .body("collectionId", equalTo(1));

        // then - 예약 목록 확인
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationRequest)
                .when().get("/reservation/me")
                .then().log().all()
                .statusCode(200)
                .body("size()", equalTo(1));

        // when - 예약 삭제
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationRequest)
                .when().delete("/reservation/1")
                .then().log().all()
                .statusCode(200);

        // then - 예약 삭제 확인
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationRequest)
                .when().get("/reservation/me")
                .then().log().all()
                .statusCode(200)
                .body("size()", equalTo(0));
    }

    @Test
    void 내_예약_및_대출_목록_조회_테스트() {
        // given
        LibraryTestFixture.createTestMember(USER_EMAIL);
        LibraryTestFixture.createTestReservation(USER_EMAIL, 1L);

        Map<String, String> request = new HashMap<>();
        request.put("email", USER_EMAIL);

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().get("/reservation/me")
                .then().log().all()
                .statusCode(200)
                .body("size()", equalTo(1));
    }

    @Test
    void 예약_삭제_권한_테스트() {
        // given
        LibraryTestFixture.createTestMember(USER_EMAIL);
        LibraryTestFixture.createTestMember(OTHER_USER_EMAIL);
        LibraryTestFixture.createTestReservation(USER_EMAIL, 1L);

        Map<String, String> wrongUserRequest = new HashMap<>();
        wrongUserRequest.put("email", OTHER_USER_EMAIL);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(wrongUserRequest)
                .when().delete("/reservation/1")
                .then().log().all()
                .statusCode(400)
                .body("message", containsString("본인의 예약만 삭제할 수 있습니다"));
    }

    @Test
    @DisplayName("회원 생성 API 테스트")
    void 회원_생성_테스트() {
        Map<String, String> memberRequest = new HashMap<>();
        memberRequest.put("email", "test@example.com");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(memberRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(201)
                .body("id", notNullValue())
                .body("email", equalTo("test@example.com"));
    }

    @Test
    @DisplayName("전체 예약 현황 조회 API 테스트")
    void 전체_예약_현황_조회_테스트() {

        Map<String, String> memberRequest = new HashMap<>();
        memberRequest.put("email", USER_EMAIL);

        LibraryTestFixture.createTestMember(USER_EMAIL);
        LibraryTestFixture.createTestReservation(USER_EMAIL, 1L);
        LibraryTestFixture.createTestReservation(USER_EMAIL, 3L);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", equalTo(2))
                .body("[0].id", notNullValue())
                .body("[0].collectionId", notNullValue())
                .body("[0].email", equalTo(USER_EMAIL))
                .body("[1].email", equalTo(USER_EMAIL));
    }
}
