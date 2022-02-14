package pl.misiejuk.dymitr.githubrepoconnection.api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents Github repository owner.
 *
 * @author Dymitr Misiejuk
 * @see GithubUserRepo
 * @since 0.0.1
 */
@Getter
@Setter
public class GithubOwner {
    private String login;
}
