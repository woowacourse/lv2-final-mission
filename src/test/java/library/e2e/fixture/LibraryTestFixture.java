package library.e2e.fixture;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {
        "spring.sql.init.data-locations=classpath:test-data.sql"
})
@ActiveProfiles("test")
public class LibraryTestFixture {

    @LocalServerPort
    int port;


    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    public static void createTestBook() {
        Map<String, Object> book = new HashMap<>();
        book.put("title", "테스트 도서");
        book.put("author", "테스트 저자");
        book.put("isbn", 9781234567890L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(book)
                .when().post("/books")
                .then().log().all()
                .statusCode(201);
    }

    public static void createTestMember(String email) {
        Map<String, String> member = new HashMap<>();
        member.put("email", email);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(member)
                .when().post("/members")
                .then().log().all()
                .statusCode(201);
    }

    public static void createTestCollection(Long bookId, String status) {
        Map<String, Object> collection = new HashMap<>();
        collection.put("bookId", bookId);
        collection.put("status", status);
        collection.put("location", "A-1-1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(collection)
                .when().post("/collections")
                .then().log().all()
                .statusCode(201);
    }

    public static void createTestReservation(String email, Long collectionId) {
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("email", email);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/collection/" + collectionId)
                .then().log().all()
                .statusCode(200);
    }

} 
