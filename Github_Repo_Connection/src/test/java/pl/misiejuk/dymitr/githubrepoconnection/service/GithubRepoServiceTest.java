package pl.misiejuk.dymitr.githubrepoconnection.service;

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

    @Test
    void shouldTakeDataFromDatabaseIfPresent() {
        //given
        Owner owner = new Owner(2L, "testUser", new ArrayList<>());
        List<GithubDataEntity> githubDataEntities = List.of(new GithubDataEntity(1L, "d", "d", owner, LocalDateTime.of(2022, 1, 1, 12, 0), 10L));
        owner.getGithubDataEntities().addAll(githubDataEntities);

        Mockito.when(githubDataEntityRepo.findGithubDataEntitiesByOwnerLogin("testUser"))
                .thenReturn(githubDataEntities);

        //when
        GithubRepoService githubRepoService = new GithubRepoService(githubClient, githubDataEntityRepo);
        GithubUserReposResponse response = githubRepoService.getGithubReposInfo("testUser");

        //then
        assertEquals(1, response.getRepos().size());
        assertEquals("d", response.getRepos().get(0).getName());
        Mockito.verify(githubClient, Mockito.times(0)).gettReposForUser(any());
        Mockito.verify(githubDataEntityRepo, Mockito.times(0)).saveAll(any());
    }

    @Test
    void shouldDownloadDataFromGithubWhenNotInDatabaseAndSaveThem(){
        //given
        Mockito.when(githubDataEntityRepo.findGithubDataEntitiesByOwnerLogin(any()))
                .thenReturn(Collections.emptyList());
        GithubUserRepo githubUserRepo = new GithubUserRepo();
        githubUserRepo.setName("repoo");
        githubUserRepo.setDescription("descroo");
        GithubOwner owner = new GithubOwner();
        owner.setLogin("testUser");
        githubUserRepo.setOwner(owner);
        githubUserRepo.setStargazersCount(10L);
        githubUserRepo.setCreationAt(ZonedDateTime.of(LocalDateTime.of(2022, 1, 1, 12, 0), ZoneId.of("UTC")));
        Mockito.when(githubClient.gettReposForUser("testUser"))
                .thenReturn(List.of(githubUserRepo));

        Owner databaseOwner = new Owner(2L, "testUser", new ArrayList<>());
        List<GithubDataEntity> githubDataEntities = List.of(new GithubDataEntity(1L, "d", "d", databaseOwner, LocalDateTime.of(2022, 1, 1, 12, 0), 10L));
        databaseOwner.getGithubDataEntities().addAll(githubDataEntities);
        Mockito.when(githubDataEntityRepo.saveAll(any())).thenReturn(githubDataEntities);

        //when
        GithubRepoService githubRepoService = new GithubRepoService(githubClient, githubDataEntityRepo);
        GithubUserReposResponse response = githubRepoService.getGithubReposInfo("testUser");

        //
        ArgumentCaptor<List> listArgumentCaptor = ArgumentCaptor.forClass(List.class);
        Mockito.verify(githubDataEntityRepo).saveAll(listArgumentCaptor.capture());

        List value = listArgumentCaptor.getValue();


        assertEquals("repoo", ((GithubDataEntity)value.get(0)).getName());

        assertEquals(1, response.getRepos().size());
        assertEquals("d", response.getRepos().get(0).getName());

    }

}