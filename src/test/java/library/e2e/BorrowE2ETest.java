package library.e2e;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class BorrowE2ETest {

    @LocalServerPort
    int port;

    private static final String USER_EMAIL = "user@example.com";

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("도서 대출 API 테스트")
    void 도서_대출_테스트() {
        Map<String, String> memberRequest = new HashMap<>();
        LibraryTestFixture.createTestMember(USER_EMAIL);
        memberRequest.put("email", USER_EMAIL);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(memberRequest)
                .when().post("/collection/2/borrow")
                .then().log().all()
                .statusCode(200)
                .body("id", notNullValue())
                .body("collectionId", equalTo(2))
                .body("memberEmail", equalTo(USER_EMAIL))
                .body("dueDate", notNullValue());
    }

}
