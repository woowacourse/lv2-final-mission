package finalmission.controller;

import finalmission.restaurant.controller.dto.request.RestaurantRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RestaurantControllerTest {

    private RestaurantRequest request;

    @BeforeEach
    public void setUp() {
        request = new RestaurantRequest("모수","모던 한식 파인다이닝", "서울시 이태원로", 50555,24);
    }


    @Test
    void test(){
        RestAssured.given().log().all()
                .contentType("application/json")
                .body(request)
                .when().post("/restaurant")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

}
