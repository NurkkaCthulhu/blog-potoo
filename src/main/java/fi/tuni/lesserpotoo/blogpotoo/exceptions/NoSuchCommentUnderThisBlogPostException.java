package fi.tuni.lesserpotoo.blogpotoo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0428
 * @since 3.0
 */
@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class NoSuchCommentUnderThisBlogPostException extends RuntimeException {

    /**
     * Creates instance of NoSuchCommentUnderThisBlogPostException with message.
     *
     * Message is in following form: "BlogPost with id [blogPostId] does not have comment with id [commentId]"
     *
     * @param blogPostId    id of BlogPost
     * @param commentId     id of Comment
     */
    public NoSuchCommentUnderThisBlogPostException(int blogPostId, int commentId) {
        super("BlogPost with id " + blogPostId + " does not have comment with id " + commentId);
    }
}
