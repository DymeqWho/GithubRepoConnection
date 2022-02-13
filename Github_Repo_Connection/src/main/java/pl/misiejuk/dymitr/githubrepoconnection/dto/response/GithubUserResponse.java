package pl.misiejuk.dymitr.githubrepoconnection.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GithubUserResponse {
    private String name;
}
