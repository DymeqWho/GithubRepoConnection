package pl.misiejuk.dymitr.githubrepoconnection.dto.response;

import lombok.Builder;
import lombok.Getter;


/**
 * Like that is represented data of Github repository owner presented for user on the desktop.
 *
 * @author Dymitr Misiejuk
 * @since 0.0.1
 */
@Getter
@Builder
public class GithubUserResponse {
    private String name;
}
