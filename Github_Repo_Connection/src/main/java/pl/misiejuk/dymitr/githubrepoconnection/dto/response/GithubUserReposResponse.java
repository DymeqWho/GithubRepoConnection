package pl.misiejuk.dymitr.githubrepoconnection.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Final representation what user of application will see on his desktop.
 *
 *  @author Dymitr Misiejuk
 *  @see GithubRepoResponse
 *  @see GithubUserResponse
 *  @since 0.0.1
 */
@Getter
@Builder
public class GithubUserReposResponse {
    private GithubUserResponse user;
    private List<GithubRepoResponse> repos;
}
