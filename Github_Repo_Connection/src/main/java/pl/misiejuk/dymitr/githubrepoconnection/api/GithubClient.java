package pl.misiejuk.dymitr.githubrepoconnection.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.misiejuk.dymitr.githubrepoconnection.api.dto.GithubUserRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubClient {
    private static final String URL_PATTERN = "https://api.github.com/users/{user}/repos";
    private final RestTemplate restTemplate;

    public List<GithubUserRepo> getReposForUser(String user) {
        TypeReference<List<GithubUserRepo>> typeReference = new TypeReference<>() {
        };
        String response = restTemplate.getForObject(URL_PATTERN, String.class, user);
        try {
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(response, typeReference);
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        }
        throw new RuntimeException("Parsing failed");
    }
}
