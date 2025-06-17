package finalmission.thirdparty.handler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.global.error.exception.BadRequestException;
import finalmission.global.error.exception.ServerException;
import finalmission.thirdparty.dto.response.RandomNameErrorResponse;
import java.io.IOException;
import java.net.URI;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class ClientErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        try {
            RandomNameErrorResponse errorResponse = objectMapper.readValue(
                    response.getBody(),
                    RandomNameErrorResponse.class
            );
            System.out.println(errorResponse.title());
            throw new BadRequestException(errorResponse.title());
        } catch (Exception e) {
            throw new ServerException("외부 서버 오류 응답을 처리하지 못했습니다.");
        }
    }
}
