package finalmission.infrastructure.client;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.domain.AnniversaryRepository;
import finalmission.infrastructure.client.config.KasiProperties;
import finalmission.infrastructure.client.dto.KasiResponse;
import finalmission.infrastructure.client.dto.KasiResponse.Item;
import finalmission.infrastructure.client.interceptor.RestClientLoggingInterceptor;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@EnableConfigurationProperties(KasiProperties.class)
public class KasiClient implements AnniversaryRepository {

    private static final String ANNIVERSARY_API_ENDPOINT = "/service/SpcdeInfoService/getRestDeInfo";

    private final RestClient restClient;
    private final KasiProperties kasiProperties;
    private final ObjectMapper objectMapper;

    public KasiClient(
        final RestClient.Builder restClientBuilder,
        final KasiProperties kasiProperties,
        final ObjectMapper objectMapper
    ) {
        this.restClient = restClientBuilder
            .requestFactory(createTimeoutFactory(kasiProperties))
            .baseUrl(kasiProperties.getBaseUrl())
            .requestInterceptor(new RestClientLoggingInterceptor())
            .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .build();
        this.kasiProperties = kasiProperties;
        this.objectMapper = objectMapper;
    }

    private BufferingClientHttpRequestFactory createTimeoutFactory(final KasiProperties properties) {
        final SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(properties.getConnectTimeout());
        factory.setReadTimeout(properties.getReadTimeout());

        return new BufferingClientHttpRequestFactory(factory);
    }

    public boolean isAnniversary(final LocalDate date) {
        final KasiResponse response = parseKasiResponse(date);

        final List<Item> items = Optional.ofNullable(response)
            .map(KasiResponse::response)
            .map(KasiResponse.Response::body)
            .map(KasiResponse.Body::items)
            .map(KasiResponse.Items::item)
            .orElse(List.of());

        return items.stream()
            .anyMatch(item ->
                item.getLocalDate().equals(date) && "Y".equals(item.isHoliday()));
    }

    private KasiResponse parseKasiResponse(final LocalDate date) {
        final String rowResponse = restClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(ANNIVERSARY_API_ENDPOINT)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 100)  // 100개를 넘는 것이 없으므로 타협..
                .queryParam("ServiceKey", kasiProperties.getSecretKey())
                .queryParam("solYear", date.getYear())
                .queryParam("solMonth", String.format("%02d", date.getMonthValue()))
                .queryParam("_type", "json")
                .build()
            )
            .retrieve()
            .body(String.class);  // 예외 상황에도 200을 반환하기에 ResponseErrorHandler 사용 불가

        final KasiResponse kasiResponse;
        try {
            kasiResponse = objectMapper.readValue(rowResponse, KasiResponse.class);
        } catch (final Exception e) {
            log.error("KASI API 응답 파싱 실패: {}", rowResponse, e);

            throw new IllegalStateException("외부 서버에서 알 수 없는 오류가 발생했습니다.");
        }
        return kasiResponse;
    }
}
