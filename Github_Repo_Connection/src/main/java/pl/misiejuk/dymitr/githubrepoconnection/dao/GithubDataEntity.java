package pl.misiejuk.dymitr.githubrepoconnection.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * Represents data stored in database. Every single repository is stored like that.
 * Many repositories may have one Owner.
 *
 * @author Dymitr Misiejuk
 * @see Owner
 * @since 0.0.1
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GithubDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    private Owner owner;
    private LocalDateTime creationAt;
    private Long stargazersCount;

}
