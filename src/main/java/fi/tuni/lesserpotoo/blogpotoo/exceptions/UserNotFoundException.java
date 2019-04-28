package fi.tuni.lesserpotoo.blogpotoo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0428
 * @since 3.0
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    /**
     * Creates instance of UserNotFoundException with message.
     *
     * Message is in following form: "User with id [id] not found."
     *
     * @param id    id of User
     */
    public UserNotFoundException(int id) {
        super("User with id " + id + " not found.");
    }
}
