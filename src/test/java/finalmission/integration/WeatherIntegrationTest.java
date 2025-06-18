package finalmission.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WeatherIntegrationTest {

    @DisplayName("외부 API를 통해 기상청 기상 정보를 가져올 수 있다.")
    @Test
    void getWeatherInfo(){
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/weathers")
                .then().statusCode(200);
    }
}
