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

    public NoNeededValuesInBodyException(String message) {
        super(message);
    }

    public static NoNeededValuesInBodyException parseException(String... valueNames) {
        String message = "RequestBody needs to have following values: ";

        for (int i = 0; i < valueNames.length; i++) {
            message += valueNames[i];

            if (i < valueNames.length -1) {
                message += ", ";
            }
        }

        return new NoNeededValuesInBodyException(message);
    }
}
