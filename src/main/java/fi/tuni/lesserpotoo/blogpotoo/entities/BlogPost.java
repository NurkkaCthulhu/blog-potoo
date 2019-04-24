package fi.tuni.lesserpotoo.blogpotoo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0423
 * @since 1.0
 */
@Entity
public class BlogPost {

    /**
     * Unique integer id
     */
    @Id
    @GeneratedValue(generator = "blogpost_seq")
    @SequenceGenerator(name = "blogpost_seq", sequenceName = "BLOGPOST_SEQ", allocationSize = 1)
    int id;

    /**
     * Author of the blogPost
     */
    @ManyToOne
    @JoinColumn
    User author;

    /**
     * Title of the blogpost
     */
    @Column(nullable = false)
    String title;

    /**
     * Content of the blogpost
     */
    @Column(length=15000)
    String content;

    /**
     * Creation date
     */
    @Column(nullable = false)
    LocalDate dateOfCreation;

    /**
     * Creation time
     */
    LocalTime timeOfCreation;

    /**
     * Cate and time of latest edit
     */
    LocalDateTime timeOfEdit;

    /**
     * Number of likes the blogpost has got
     */
    int likes;

    /**
     * All comments the blogpost has
     */
    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Comment> comments = new HashSet<>();

    /**
     * All tags the blogpost has
     */
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "blogpost_tag",
            joinColumns = @JoinColumn(name = "blogpost_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    Set<Tag> tags;

    /**
     * Default constructor.
     *
     * Gets date and time of creation automatically.
     */
    public BlogPost() {
        this.dateOfCreation = LocalDate.now();
        this.timeOfCreation = LocalTime.now();
        this.tags = new LinkedHashSet<>();
    }

    /**
     * Constructor with author, title and content.
     *
     * Get date and time of creation automatically.
     *
     * @param author
     * @param title
     * @param content
     */
    public BlogPost(User author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.dateOfCreation = LocalDate.now();
        this.timeOfCreation = LocalTime.now();
        this.tags = new LinkedHashSet<>();
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
     * Returns author.
     *
     * @return author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Sets author.
     *
     * @param author
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * Returns title.
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns content.
     *
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets content.
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns date of creation.
     *
     * @return date of creation
     */
    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    /**
     * Returns time of creation.
     *
     * @return time of creation
     */
    public LocalTime getTimeOfCreation() {
        return timeOfCreation;
    }

    /**
     * Returns date and time of edit.
     *
     * @return date and time of edit
     */
    public LocalDateTime getTimeOfEdit() {
        return timeOfEdit;
    }

    /**
     * Sets date and time of edit.
     *
     * @param timeOfEdit
     */
    public void setTimeOfEdit(LocalDateTime timeOfEdit) {
        this.timeOfEdit = timeOfEdit;
    }

    /**
     * Returns likes.
     *
     * @return likes
     */
    public int getLikes() {
        return likes;
    }

    /**
     * Sets likes.
     *
     * @param likes
     */
    public void setLikes(int likes) {
        this.likes = likes;
    }

    /**
     * Returns tags.
     *
     * @return tags
     */
    public Set<Tag> getTags() {
        return tags;
    }

    /**
     * Sets tags.
     *
     * @param tags
     */
    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Gets comments.
     *
     * @return comments.
     */
    public Set<Comment> getComments() {
        return comments;
    }

    /**
     * Returns string representation of the blogPost
     * @return
     */
    @Override
    public String toString() {
        return "BlogPost{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", dateOfCreation='" + dateOfCreation + '\'' +
                ", timeOfCreation=" + timeOfCreation +
                ", timeOfEdit=" + timeOfEdit +
                ", likes=" + likes +
                '}';
    }
}
