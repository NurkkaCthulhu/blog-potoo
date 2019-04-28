package fi.tuni.lesserpotoo.blogpotoo.exceptions;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0428
 * @since 3.1
 */
public class NoNeededValuesInBodyException extends RuntimeException {

    public NoNeededValuesInBodyException(String... valueNames) {
        super("RequestBody needs to have following values: " + valueNames);
    }
}
