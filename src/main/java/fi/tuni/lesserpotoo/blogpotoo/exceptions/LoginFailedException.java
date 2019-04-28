package fi.tuni.lesserpotoo.blogpotoo.exceptions;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0428
 * @since 3.0
 */
public class LoginFailedException extends RuntimeException {
    public LoginFailedException() {
        super("Username or password is incorrect.");
    }
}
