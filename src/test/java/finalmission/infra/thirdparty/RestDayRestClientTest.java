package finalmission.infra.thirdparty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.infra.thirdparty.dto.RestDayRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class RestDayRestClientTest {

    @Autowired
    RestDayRestClient restDayRestClient;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void openApiTest() {

        // given
        RestDayRequest restDayRequest = new RestDayRequest(2025, 5, 25);
        ResponseEntity<String> response = restDayRestClient.getRestDay(restDayRequest);

//        JsonNode jsonNode = getJsonNode(response.getBody());

        System.out.println(response);
//        int totalCount = jsonNode
//                .path("response")
//                .path("body")
//                .path("totalCount")
//                .asInt();
//
//        System.out.println(response.getBody());
//        assertThat(totalCount).isEqualTo(3);
    }

    private JsonNode getJsonNode(String body) {
        try {
            return objectMapper.readTree(body);
        } catch (JsonProcessingException e) {
            throw new OpenApiException(e.getMessage());
        }
    }

}
