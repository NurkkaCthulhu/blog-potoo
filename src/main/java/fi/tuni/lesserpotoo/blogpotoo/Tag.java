package fi.tuni.lesserpotoo.blogpotoo;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Tag {
    @Id
    @GeneratedValue(generator = "tag_seq")
    @SequenceGenerator(name = "tag_seq", sequenceName = "TAG_SEQ", allocationSize = 1)
    int id;

    String tagName;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    Set<BlogPost> blogPosts;

    public Tag() {}

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getId() {
        return id;
    }

    public Set<BlogPost> getBlogPosts() {
        return blogPosts;
    }

    public void setBlogPosts(Set<BlogPost> blogPosts) {
        this.blogPosts = blogPosts;
    }
}
