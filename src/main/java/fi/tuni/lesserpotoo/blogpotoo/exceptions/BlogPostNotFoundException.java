package fi.tuni.lesserpotoo.blogpotoo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0428
 * @since 3.0
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BlogPostNotFoundException extends RuntimeException {

    /**
     * Creates instance of BlogPostNotFoundException with message.
     *
     * Message is always in following form: "BlogPost with id [id] not found."
     *
     * @param id    BlogPost id
     */
    public BlogPostNotFoundException(int id) {
        super("BlogPost with id " + id + " not found.");
    }
}