package library.book.infrastructure;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import library.book.dto.BookResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Component
public class BookApiClient {

    private static final String BOOK_URL = "https://openapi.naver.com/v1/search/book.json";

    private final RestClient restClient;
    private final HttpHeaders defaultHeaders;

    public BookApiClient(RestClient.Builder restClientBuilder,
                         @Value("${naver.client-id}") String clientId,
                         @Value("${naver.client-secret}") String clientSecret) {
        this.restClient = restClientBuilder.baseUrl(BOOK_URL).build();

        this.defaultHeaders = new HttpHeaders();
        defaultHeaders.setContentType(MediaType.APPLICATION_JSON);
        defaultHeaders.set("X-Naver-Client-Id", clientId);
        defaultHeaders.set("X-Naver-Client-Secret", clientSecret);
    }

    public BookResponse searchBook(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            URI uri = URI.create(BOOK_URL + "?query=" + encodedQuery);

            return restClient.get()
                    .uri(uri)
                    .headers(headers -> headers.addAll(defaultHeaders))
                    .retrieve()
                    .body(BookResponse.class);

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new RuntimeException("API 요청 오류: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (RestClientResponseException e) {
            throw new RuntimeException("API 응답 처리 중 오류 발생: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("책 검색 요청 실패", e);
        }
    }
}
