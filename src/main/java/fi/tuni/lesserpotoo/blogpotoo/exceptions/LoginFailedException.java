package fi.tuni.lesserpotoo.blogpotoo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0428
 * @since 3.0
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LoginFailedException extends RuntimeException {
    public LoginFailedException() {
        super("Username or password is incorrect.");
    }
}
