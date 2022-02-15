package pl.misiejuk.dymitr.githubrepoconnection.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.misiejuk.dymitr.githubrepoconnection.api.GithubClient;
import pl.misiejuk.dymitr.githubrepoconnection.api.dto.GithubOwner;
import pl.misiejuk.dymitr.githubrepoconnection.api.dto.GithubUserRepo;
import pl.misiejuk.dymitr.githubrepoconnection.dao.GithubDataEntity;
import pl.misiejuk.dymitr.githubrepoconnection.dao.Owner;
import pl.misiejuk.dymitr.githubrepoconnection.database.GithubDataEntityRepo;
import pl.misiejuk.dymitr.githubrepoconnection.dto.response.GithubUserReposResponse;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class GithubRepoServiceTest {

    @Mock
    GithubClient githubClient;
    @Mock
    GithubDataEntityRepo githubDataEntityRepo;

    private GithubRepoService githubRepoService;
    private String testUser;

    @BeforeEach
    void setUp() {
        this.githubRepoService = new GithubRepoService(githubClient, githubDataEntityRepo);
        this.testUser = "testUser";
    }

    @Test
    void shouldTakeDataFromDatabaseIfPresent() {
        //given
        Owner owner = presentExampleOwner(testUser);
        List<GithubDataEntity> githubDataEntities = presentExampleGithubDataEntities(owner);
        owner.getGithubDataEntities().addAll(githubDataEntities);

        Mockito.when(githubDataEntityRepo.findGithubDataEntitiesByOwnerLogin("testUser"))
                .thenReturn(githubDataEntities);

        //when
        GithubUserReposResponse response = githubRepoService.getGithubReposInfo("testUser");

        //then
        assertEquals(1, response.getRepos().size());
        assertEquals("d", response.getRepos().get(0).getName());
        assertEquals("d", response.getRepos().get(0).getDescription());
        assertEquals("01-01-2022 12:00", response.getRepos().get(0).getCreationAt());
        assertEquals(10L, response.getRepos().get(0).getStargazersCount());

        assertNotEquals(2, response.getRepos().size());
        assertNotEquals("invalidName", response.getRepos().get(0).getName());
        assertNotEquals("invalidDesc", response.getRepos().get(0).getDescription());
        assertNotEquals("02-02-2222 12:22", response.getRepos().get(0).getCreationAt());
        assertNotEquals(15L, response.getRepos().get(0).getStargazersCount());

        Mockito.verify(githubClient, Mockito.times(0)).getReposForUser(any());
        Mockito.verify(githubDataEntityRepo, Mockito.times(0)).saveAll(any());
    }

    @Test
    void shouldDownloadDataFromGithubWhenNotInDatabaseAndSaveThem() {
        //given
        Mockito.when(githubDataEntityRepo.findGithubDataEntitiesByOwnerLogin(any()))
                .thenReturn(Collections.emptyList());

        GithubUserRepo githubUserRepo = showExampleGithubUserRepo();

        GithubOwner owner = showExampleGithubOwner();

        githubUserRepo.setOwner(owner);
        githubUserRepo.setStargazersCount(10L);
        githubUserRepo.setCreationAt(showUtc());
        Mockito.when(githubClient.getReposForUser(testUser))
                .thenReturn(List.of(githubUserRepo));

        Owner databaseOwner = new Owner(2L, testUser, new ArrayList<>());
        List<GithubDataEntity> githubDataEntities = presentExampleGithubDataEntities(databaseOwner);
        databaseOwner.getGithubDataEntities().addAll(githubDataEntities);
        Mockito.when(githubDataEntityRepo.saveAll(any())).thenReturn(githubDataEntities);

        //when
        GithubRepoService githubRepoService = new GithubRepoService(githubClient, githubDataEntityRepo);
        GithubUserReposResponse response = githubRepoService.getGithubReposInfo("testUser");

        //then
        ArgumentCaptor<List> listArgumentCaptor = ArgumentCaptor.forClass(List.class);
        Mockito.verify(githubDataEntityRepo).saveAll(listArgumentCaptor.capture());

        List value = listArgumentCaptor.getValue();


        assertEquals("repoo", ((GithubDataEntity) value.get(0)).getName());

        assertEquals(1, response.getRepos().size());
        assertEquals("d", response.getRepos().get(0).getName());

    }

    private ZonedDateTime showUtc() {
        return ZonedDateTime.of(LocalDateTime.of(2022, 1, 1, 12, 0), ZoneId.of("UTC"));
    }

    private GithubOwner showExampleGithubOwner() {
        GithubOwner owner = new GithubOwner();
        owner.setLogin(testUser);
        return owner;
    }

    private GithubUserRepo showExampleGithubUserRepo() {
        GithubUserRepo githubUserRepo = new GithubUserRepo();
        githubUserRepo.setName("repoo");
        githubUserRepo.setDescription("descroo");
        return githubUserRepo;
    }

    private List<GithubDataEntity> presentExampleGithubDataEntities(Owner owner) {
        return List.of(new GithubDataEntity(1L, "d", "d", owner, LocalDateTime.of(2022, 1, 1, 12, 0), 10L));
    }

    private Owner presentExampleOwner(String testUser) {
        return new Owner(2L, testUser, new ArrayList<>());
    }

}