package pl.misiejuk.dymitr.githubrepoconnection.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GithubUserReposResponse {
    private GithubUserResponse user;
    private List<GithubRepoResponse> repos;
}
