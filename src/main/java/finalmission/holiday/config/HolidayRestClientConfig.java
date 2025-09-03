package finalmission.holiday.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;

@Configuration
public class HolidayRestClientConfig {

    @Value("${restclient.timeout.connection:3000}")
    private int connectionTimeout;

    @Value("${restclient.timeout.read:5000}")
    private int readTimeout;

    @Bean
    public RestClient holidayRestClient(RestClient.Builder restClientBuilder) {
        return restClientBuilder
                .uriBuilderFactory(uriBuilderFactory(""))
                .requestFactory(simpleClientHttpRequestFactory())
                .build();
    }
    
    // 타임아웃 설정
    private SimpleClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectionTimeout);
        requestFactory.setReadTimeout(readTimeout);
        return requestFactory;
    }

    // uri 인코딩 안함 설정
    private DefaultUriBuilderFactory uriBuilderFactory(String baseUrl) {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(baseUrl);
        uriBuilderFactory.setEncodingMode(EncodingMode.NONE);
        return uriBuilderFactory;
    }
}
