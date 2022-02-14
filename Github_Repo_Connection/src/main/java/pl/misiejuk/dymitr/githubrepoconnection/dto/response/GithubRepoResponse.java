package pl.misiejuk.dymitr.githubrepoconnection.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * Like that is represented data of single repository presented for user on the desktop.
 *
 * @author Dymitr Misiejuk
 * @since 0.0.1
 */
@Getter
@Builder
public class GithubRepoResponse {
    private String name;
    private String description;
    private String creationAt;
    private Long stargazersCount;
}
