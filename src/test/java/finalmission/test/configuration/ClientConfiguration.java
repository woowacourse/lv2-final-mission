package finalmission.test.configuration;

import finalmission.client.RandomNameClient;
import finalmission.test.stub.RandomNameClientStub;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class ClientConfiguration {

    @Bean
    public RandomNameClient randomNameClient() {
        return new RandomNameClientStub();
    }
}
