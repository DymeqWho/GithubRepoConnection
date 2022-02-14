package pl.misiejuk.dymitr.githubrepoconnection.api;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Is responsible to perform HTTP requests.
 *
 * @author Dymitr Misiejuk
 * @see GithubClient
 * @since 0.0.1
 */
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
}
