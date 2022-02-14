package pl.misiejuk.dymitr.githubrepoconnection.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.misiejuk.dymitr.githubrepoconnection.dto.response.GithubUserReposResponse;
import pl.misiejuk.dymitr.githubrepoconnection.service.GithubRepoService;

/**
 * Has method responsible for calling on user's path method responsible for getting information from the Github repository.
 * There begins and ends application.
 *
 * @author Dymitr Misiejuk
 * @see GithubRepoService
 * @since 0.0.1
 */
@RestController
@RequiredArgsConstructor
public class UserReposController {
    private final GithubRepoService githubRepoService;

    @GetMapping(path = "/user/{username}")
    public GithubUserReposResponse getUserRepos(@PathVariable("username") String name) {
        return githubRepoService.getGithubReposInfo(name);
    }

}
