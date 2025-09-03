package library.e2e;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import io.restassured.RestAssured;
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
class BookSearchE2ETest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("GET /books/search로 책을 검색할 수 있다")
    void searchBooks_성공() {
        // given
        String query = "자바";

        // when & then
        RestAssured.given().log().all()
                .queryParam("query", query)
                .when().get("/books/search")
                .then().log().all()
                .statusCode(200)
                .body("items", notNullValue())
                .body("items", hasSize(greaterThan(0)))
                .body("items[0].title", notNullValue())
                .body("items[0].author", notNullValue())
                .body("items[0].isbn", notNullValue())
                .body("items[0].description", notNullValue())
                .body("items[0].title", containsString("자바"));
    }
}
