package pl.misiejuk.dymitr.githubrepoconnection.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GithubRepoResponse {
    private String name;
    private String description;
    private String creationAt;
    private Long stargazersCount;
}
