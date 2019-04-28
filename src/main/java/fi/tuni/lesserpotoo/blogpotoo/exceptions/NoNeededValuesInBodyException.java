package fi.tuni.lesserpotoo.blogpotoo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0428
 * @since 3.1
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoNeededValuesInBodyException extends RuntimeException {

    public NoNeededValuesInBodyException(String... valueNames) {
        super("RequestBody needs to have following values: " + valueNames);
    }
}
