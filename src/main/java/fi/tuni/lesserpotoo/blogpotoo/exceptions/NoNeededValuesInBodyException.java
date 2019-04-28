package fi.tuni.lesserpotoo.blogpotoo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0428
 * @since 3.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoNeededValuesInBodyException extends RuntimeException {

    /**
     * Creates instance of NoNeededValuesInBodyException with given error message.
     *
     * @param message
     */
    public NoNeededValuesInBodyException(String message) {
        super(message);
    }

    /**
     * Parses a message from given value names and returns NoNeededValuesInBodyException object with that message.
     *
     * Goes trough list of strings and adds them to string, that is used as message. Message is in following form:
     * "RequestBody needs to have following keys with values: [list of values]"
     *
     * @param keys   keys to values, that were not found
     * @return NoNeededValuesInBodyException with parsed message
     */
    public static NoNeededValuesInBodyException parseException(String... keys) {
        String message = "RequestBody needs to have following keys with values: ";

        for (int i = 0; i < keys.length; i++) {
            message += keys[i];

            if (i < keys.length -1) {
                message += ", ";
            }
        }

        return new NoNeededValuesInBodyException(message);
    }
}
