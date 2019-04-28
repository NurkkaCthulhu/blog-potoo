package fi.tuni.lesserpotoo.blogpotoo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0428
 * @since 3.1
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BlogPostNotFoundException extends RuntimeException {
    public BlogPostNotFoundException(int id) {
        super("BlogPost with id " + id + " not found.");
    }
}