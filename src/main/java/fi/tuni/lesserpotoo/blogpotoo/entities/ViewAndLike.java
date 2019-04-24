package fi.tuni.lesserpotoo.blogpotoo.entities;

import fi.tuni.lesserpotoo.blogpotoo.misc.CompositeKey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0424
 * @since 3.0
 */
@Entity
@IdClass(CompositeKey.class)
public class ViewAndLike {

    /**
     * Id of the user, first part of id.
     */
    @Id
    private int userId;

    /**
     * Id of the blogPost, last part of id.
     */
    @Id
    private int blogPostId;

    /**
     * Has blogPost been viewed by this user
     */
    @Column(nullable = false)
    private boolean viewed;

    /**
     * Has blogPost been liked by this user
     */
    @Column(nullable = false)
    private boolean liked;

    /**
     * Default constructor.
     */
    public ViewAndLike() {
        this.viewed = false;
        this.liked = false;
    }

    /**
     * Cosntructor with userId, blogPostId, viewed and liked.
     *
     * @param userId
     * @param blogPostId
     * @param viewed
     * @param liked
     */
    public ViewAndLike(int userId, int blogPostId, boolean viewed, boolean liked) {
        this.userId = userId;
        this.blogPostId = blogPostId;
        this.viewed = viewed;
        this.liked = liked;
    }

    /**
     * Returns the id of the user.
     *
     * @return id of the user
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Returns the id of the blogPost.
     *
     * @return id of the blogPost
     */
    public int getBlogPostId() {
        return blogPostId;
    }

    /**
     * Returns viewed.
     *
     * @return viewed
     */
    public boolean isViewed() {
        return viewed;
    }

    /**
     * Sets viewed.
     *
     * @param viewed
     */
    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    /**
     * Returns liked.
     *
     * @return liked
     */
    public boolean isLiked() {
        return liked;
    }

    /**
     * Sets liked.
     *
     * @param liked
     */
    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    /**
     * Returns string representation of the viewAndLike
     *
     * @return string representation of the viewAndLike
     */
    @Override
    public String toString() {
        return "ViewAndLike{" +
                "userId=" + userId +
                ", blogPostId=" + blogPostId +
                ", viewed=" + viewed +
                ", liked=" + liked +
                '}';
    }
}
