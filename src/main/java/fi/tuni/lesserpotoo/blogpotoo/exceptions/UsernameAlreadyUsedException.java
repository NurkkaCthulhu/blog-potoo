package fi.tuni.lesserpotoo.blogpotoo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0428
 * @since 3.0
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UsernameAlreadyUsedException extends RuntimeException {

    /**
     * Creates instance of UsernameAlreadyUsedException with message.
     *
     * Message is in following form: "Username "[username]" already in use."
     *
     * @param username
     */
    public UsernameAlreadyUsedException(String username) {
        super("Username \"" + username + "\" already in use.");
    }
}
