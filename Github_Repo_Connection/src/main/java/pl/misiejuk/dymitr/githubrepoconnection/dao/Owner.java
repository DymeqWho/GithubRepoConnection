package pl.misiejuk.dymitr.githubrepoconnection.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Represents data stored in database. Owner is a person, whose repositories we would like to check.
 * One Owner may have multiple repositories.
 *
 * @author Dymitr Misiejuk
 * @see GithubDataEntity
 * @since 0.0.1
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    @OneToMany(mappedBy = "id")
    private List<GithubDataEntity> githubDataEntities;
}
