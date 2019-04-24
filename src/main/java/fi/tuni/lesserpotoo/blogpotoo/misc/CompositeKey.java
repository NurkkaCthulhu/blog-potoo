package fi.tuni.lesserpotoo.blogpotoo.misc;

import java.io.Serializable;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0424
 * @since 3.0
 *
 * Used as id in ViewAndLike.
 */
public class CompositeKey implements Serializable {

    /**
     * The id of the user.
     */
    private int userId;

    /**
     * The id of the blogPost.
     */
    private int blogPostId;
}
