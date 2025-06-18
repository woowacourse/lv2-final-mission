package finalmission.integration;

import finalmission.customer.dto.request.CustomerCreateRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CustomerIntegrationTest {

    @DisplayName("회원가입 테스트")
    @Test
    void registerCustomer(){
        CustomerCreateRequest createRequest = new CustomerCreateRequest("id","password");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON).body(createRequest)
                .when().post("/customers")
                .then().log().all().statusCode(200);
    }
}
