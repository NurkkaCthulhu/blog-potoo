package fi.tuni.lesserpotoo.blogpotoo.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0424
 * @since 3.0
 */
@Entity
public class Comment {

    /**
     * Unique integer id
     */
    @Id
    @GeneratedValue(generator = "comment_seq")
    @SequenceGenerator(name = "comment_seq", sequenceName = "COMMENT_SEQ", allocationSize = 1)
    int id;

    /**
     * Commenter of the comment
     */
    @ManyToOne
    @JoinColumn
    User commenter;

    /**
     * BlogPost the comment belongs to
     */
    @ManyToOne
    @JoinColumn
    BlogPost blogPost;

    /**
     * Date of comment creation
     */
    @Column(nullable = false)
    LocalDate dateOfComment;

    /**
     * Time of comment creation
     */
    @Column(nullable = false)
    LocalTime timeOfComment;

    /**
     * Content of the comment
     */
    @Column(nullable = false, length = 2000)
    String content;

    /**
     * Default constructor.
     *
     * Sets date and time of creation automatically.
     */
    public Comment() {
        dateOfComment = LocalDate.now();
        timeOfComment = LocalTime.now();
    }

    /**
     * Constructor with commenter, blogPost and content.
     *
     * Sets date and time of creation automatically.
     *
     * @param commenter
     * @param blogPost
     * @param content
     */
    public Comment(User commenter, BlogPost blogPost, String content) {
        this.commenter = commenter;
        this.blogPost = blogPost;
        this.content = content;
        dateOfComment = LocalDate.now();
        timeOfComment = LocalTime.now();
    }

    /**
     * Returns date of comment creation.
     *
     * @return date of creation
     */
    public LocalDate getDateOfComment() {
        return dateOfComment;
    }

    /**
     * Sets date of comment creation.
     *
     * @param dateOfComment
     */
    public void setDateOfComment(LocalDate dateOfComment) {
        this.dateOfComment = dateOfComment;
    }

    /**
     * Returns time of comment creation.
     *
     * @return time of creation
     */
    public LocalTime getTimeOfComment() {
        return timeOfComment;
    }

    /**
     * Sets time of comment creation.
     *
     * @param timeOfComment
     */
    public void setTimeOfComment(LocalTime timeOfComment) {
        this.timeOfComment = timeOfComment;
    }

    /**
     * Returns content of the comment.
     *
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets content of the comment.
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns id.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns commenter.
     *
     * @return commenter
     */
    public User getCommenter() {
        return commenter;
    }

    /**
     * Returns blogpPst.
     *
     * @return blogPost
     */
    public BlogPost getBlogPost() {
        return blogPost;
    }

    /**
     * Returns string representation of the comment
     * @return string representation of the comment
     */
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", commentor=" + commenter +
                ", blogPost=" + blogPost +
                ", dateOfComment=" + dateOfComment +
                ", timeOfComment=" + timeOfComment +
                ", content='" + content + '\'' +
                '}';
    }
}
