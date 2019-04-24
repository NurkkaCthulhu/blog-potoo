package fi.tuni.lesserpotoo.blogpotoo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0328
 * @since 2.0
 */
@Entity
public class Tag {

    /**
     * Unique integer id
     */
    @Id
    @GeneratedValue(generator = "tag_seq")
    @SequenceGenerator(name = "tag_seq", sequenceName = "TAG_SEQ", allocationSize = 1)
    int id;

    /**
     * Name of the tag
     */
    String tagName;

    /**
     * Set of all blogPosts that have the tag.
     */
    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    @JsonIgnore
    Set<BlogPost> blogPosts;

    /**
     * Default constructor.
     */
    public Tag() {}

    /**
     * Constructor with tagName.
     *
     * @param tagName
     */
    public Tag(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Returns tagName.
     *
     * @return tagName
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * Sets tagName.
     *
     * @param tagName
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
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
     * Returns all BlogPosts that have this tag.
     *
     * @return
     */
    public Set<BlogPost> getBlogPosts() {
        return blogPosts;
    }

    /**
     * Sets all BlogPosts that have this tag.
     *
     * @param blogPosts
     */
    public void setBlogPosts(Set<BlogPost> blogPosts) {
        this.blogPosts = blogPosts;
    }
}
