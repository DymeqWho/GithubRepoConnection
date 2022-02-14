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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GithubRepoService {
    private final GithubClient githubClient;
    private final GithubDataEntityRepo githubDataEntityRepo;

    public GithubUserReposResponse getGithubReposInfo(String user) {
        List<GithubDataEntity> githubDataEntitiesByOwnerLogin = githubDataEntityRepo.findGithubDataEntitiesByOwnerLogin(user);
        if (githubDataEntitiesByOwnerLogin.isEmpty()) {
            List<GithubUserRepo> githubUserRepos = githubClient.getReposForUser(user);
            if (!githubUserRepos.isEmpty()) {
                Owner owner = new Owner(null, user, new ArrayList<>());
                List<GithubDataEntity> githubDataEntities = githubUserRepos.stream().map(x ->
                        new GithubDataEntity(null, x.getName(), x.getDescription(), owner, x.getCreationAt().toLocalDateTime(), x.getStargazersCount())
                ).collect(Collectors.toList());
                githubDataEntities.forEach(x -> owner.getGithubDataEntities().add(x));
                githubDataEntitiesByOwnerLogin = githubDataEntityRepo.saveAll(githubDataEntities);
            }
        }
        return GithubUserReposResponse.builder()
                .repos(githubDataEntitiesByOwnerLogin.stream()
                        .map(entity -> GithubRepoResponse.builder()
                                .name(entity.getName())
                                .description(entity.getDescription())
                                .creationAt(entity.getCreationAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")))
                                .stargazersCount(entity.getStargazersCount())
                                .build())
                        .collect(Collectors.toList())
                ).user(Optional.ofNullable(githubDataEntitiesByOwnerLogin.get(0))
                        .map(GithubDataEntity::getOwner)
                        .map(owner -> GithubUserResponse.builder()
                                .name(owner.getLogin())
                                .build())
                        .orElseThrow(() -> new RuntimeException("No user")))
                .build();
    }
}
