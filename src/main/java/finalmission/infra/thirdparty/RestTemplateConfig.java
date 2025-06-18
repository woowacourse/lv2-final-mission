package finalmission.infra.thirdparty;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean("SendGridRestTemplate")
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);
        requestFactory.setReadTimeout(5000);

        return new RestTemplate(requestFactory);
    }

    @Bean
    public SendGridErrorResponseFilter sendGridErrorResponseFilter(ObjectMapper objectMapper) {
        return new SendGridErrorResponseFilter(objectMapper);
    }
} 
