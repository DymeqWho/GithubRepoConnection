package pl.misiejuk.dymitr.githubrepoconnection.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * Represents Github repository owned by user.
 *
 * @author Dymitr Misiejuk
 * @see GithubOwner
 * @see DateHandler
 * @since 0.0.1
 */
@Getter
@Setter
public class GithubUserRepo {
    private String name;
    private String description;
    private GithubOwner owner;

    @JsonProperty("created_at")
    @JsonDeserialize(using = DateHandler.class)
    private ZonedDateTime creationAt;

    @JsonProperty("stargazers_count")
    private Long stargazersCount;

}
