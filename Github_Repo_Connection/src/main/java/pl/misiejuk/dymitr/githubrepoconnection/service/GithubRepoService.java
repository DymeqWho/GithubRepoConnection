package pl.misiejuk.dymitr.githubrepoconnection.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.misiejuk.dymitr.githubrepoconnection.api.GithubClient;
import pl.misiejuk.dymitr.githubrepoconnection.api.dto.GithubUserRepo;
import pl.misiejuk.dymitr.githubrepoconnection.dao.GithubDataEntity;
import pl.misiejuk.dymitr.githubrepoconnection.dao.Owner;
import pl.misiejuk.dymitr.githubrepoconnection.database.GithubDataEntityRepo;
import pl.misiejuk.dymitr.githubrepoconnection.dto.response.GithubRepoResponse;
import pl.misiejuk.dymitr.githubrepoconnection.dto.response.GithubUserReposResponse;
import pl.misiejuk.dymitr.githubrepoconnection.dto.response.GithubUserResponse;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Contain logic of storing data in repository from Github client. It checks if data is already stored in repository, if yes,
 * then it's taken from repository if no it is first downloaded from Github and stored in repository in desired configuration of JSON.
 *
 * @author Dymitr Misiejuk
 * @see GithubClient
 * @see GithubUserRepo
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class GithubRepoService {
    private final GithubClient githubClient;
    private final GithubDataEntityRepo githubDataEntityRepo;

    public GithubUserReposResponse getGithubReposInfo(String user) {
        List<GithubDataEntity> githubDataEntitiesByOwnerLogin = githubDataEntityRepo.findGithubDataEntitiesByOwnerLogin(user);
        if (githubDataEntitiesByOwnerLogin.isEmpty()) {
            githubDataEntitiesByOwnerLogin = getGithubDataEntities(user, githubDataEntitiesByOwnerLogin);
        }
        return convertDataEntityToGithubUserReposResponse(githubDataEntitiesByOwnerLogin);
    }

    private List<GithubDataEntity> getGithubDataEntities(String user, List<GithubDataEntity> githubDataEntitiesByOwnerLogin) {
        List<GithubUserRepo> githubUserRepos = githubClient.getReposForUser(user);
        if (!githubUserRepos.isEmpty()) {
            Owner owner = new Owner(null, user, new ArrayList<>());
            githubDataEntitiesByOwnerLogin = changeGithubUserRepoOnGithubDataEntities(githubUserRepos, owner);
        }
        return githubDataEntitiesByOwnerLogin;
    }

    private List<GithubDataEntity> changeGithubUserRepoOnGithubDataEntities(List<GithubUserRepo> githubUserRepos, Owner owner) {
        List<GithubDataEntity> githubDataEntities = githubUserRepos.stream().map(x ->
                new GithubDataEntity(null, x.getName(), x.getDescription(), owner, x.getCreationAt().toLocalDateTime(), x.getStargazersCount())
        ).collect(Collectors.toList());
        githubDataEntities.forEach(x -> owner.getGithubDataEntities().add(x));
        return githubDataEntityRepo.saveAll(githubDataEntities);
    }

    private GithubUserReposResponse convertDataEntityToGithubUserReposResponse(List<GithubDataEntity> githubDataEntitiesByOwnerLogin) {
        return GithubUserReposResponse.builder()
                .repos(getListOfGithubRepoResponse(githubDataEntitiesByOwnerLogin)
                ).user(getGithubUserResponse(githubDataEntitiesByOwnerLogin))
                .build();
    }

    private List<GithubRepoResponse> getListOfGithubRepoResponse(List<GithubDataEntity> githubDataEntitiesByOwnerLogin) {
        return githubDataEntitiesByOwnerLogin.stream()
                .map(entity -> GithubRepoResponse.builder()
                        .name(entity.getName())
                        .description(entity.getDescription())
                        .creationAt(entity.getCreationAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")))
                        .stargazersCount(entity.getStargazersCount())
                        .build())
                .collect(Collectors.toList());
    }

    private GithubUserResponse getGithubUserResponse(List<GithubDataEntity> githubDataEntitiesByOwnerLogin) {
        return Optional.ofNullable(githubDataEntitiesByOwnerLogin.get(0))
                .map(GithubDataEntity::getOwner)
                .map(getOwnerGithubUserResponseFunction())
                .orElseThrow(() -> new RuntimeException("No user"));
    }

    private Function<Owner, GithubUserResponse> getOwnerGithubUserResponseFunction() {
        return owner -> GithubUserResponse.builder()
                .name(owner.getLogin())
                .build();
    }
}
