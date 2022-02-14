package pl.misiejuk.dymitr.githubrepoconnection.database;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.misiejuk.dymitr.githubrepoconnection.dao.GithubDataEntity;

import java.util.List;

/**
 * Used for access to Repository, used for finding data by provided parameter.
 *
 * @author Dymitr Misiejuk
 * @see GithubDataEntity
 * @since 0.0.1
 */
public interface GithubDataEntityRepo extends JpaRepository<GithubDataEntity, Long> {

    List<GithubDataEntity> findGithubDataEntitiesByOwnerLogin(String user);
}
