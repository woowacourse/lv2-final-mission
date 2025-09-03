package finalmission.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.infra.thirdparty.OpenApiException;
import finalmission.infra.thirdparty.RestDayRestClient;
import finalmission.infra.thirdparty.dto.RestDayRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RestDataService {

    private final RestDayRestClient restDayRestClient;
    private final ObjectMapper objectMapper;

    public RestDataService(final RestDayRestClient restDayRestClient, final ObjectMapper objectMapper) {
        this.restDayRestClient = restDayRestClient;
        this.objectMapper = objectMapper;
    }


    public boolean checkRestDay(RestDayRequest restDayRequest) {
        ResponseEntity<String> response = restDayRestClient.getRestDay(restDayRequest);
        JsonNode root = getJsonNode(response.getBody());
        JsonNode itemsNode = root.path("response")
                .path("body")
                .path("items")
                .path("item");
        int today = Integer.parseInt(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        for (JsonNode item : itemsNode) {
            if (item.path("locdate").asInt() == today) {
                return true;
            }
        }
        return false;
    }

    private JsonNode getJsonNode(String body) {
        try {
            return objectMapper.readTree(body);
        } catch (JsonProcessingException e) {
            throw new OpenApiException(e.getMessage());
        }
    }
}
