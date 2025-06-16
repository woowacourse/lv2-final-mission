package finalmission.customer.controller;

import static org.junit.jupiter.api.Assertions.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerControllerTest {

    @LocalServerPort
    private int port;

    private Map<String, String> customerInfo;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        customerInfo = new HashMap<>();
        customerInfo.put("name", "neo");
        customerInfo.put("email", "neo@com");
        customerInfo.put("password", "1234");
    }

    @Test
    @DisplayName("회원을 저장한다.")
    void saveTest() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(customerInfo)
                .when().post("/customers")
                .then().log().all()
                .statusCode(201);
    }

}
