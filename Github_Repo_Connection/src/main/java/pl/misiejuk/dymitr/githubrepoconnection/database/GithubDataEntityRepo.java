package pl.misiejuk.dymitr.githubrepoconnection.database;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.misiejuk.dymitr.githubrepoconnection.dao.GithubDataEntity;

import java.util.List;

public interface GithubDataEntityRepo extends JpaRepository<GithubDataEntity, Long> {

    List<GithubDataEntity> findGithubDataEntitiesByOwnerLogin(String user);
}
