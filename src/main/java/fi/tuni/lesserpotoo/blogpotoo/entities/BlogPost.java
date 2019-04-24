package fi.tuni.lesserpotoo.blogpotoo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class BlogPost {

    @Id
    @GeneratedValue(generator = "blogpost_seq")
    @SequenceGenerator(name = "blogpost_seq", sequenceName = "BLOGPOST_SEQ", allocationSize = 1)
    int id;

    @ManyToOne
    @JoinColumn
    User author;

    @Column(nullable = false)
    String title;
    @Column(length=15000)
    String content;

    @Column(nullable = false)
    LocalDate dateOfCreation;
    LocalTime timeOfCreation;
    LocalDateTime timeOfEdit;

    int likes;

    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Comment> comments = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "blogpost_tag",
            joinColumns = @JoinColumn(name = "blogpost_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    Set<Tag> tags;

    public BlogPost() {
        this.dateOfCreation = LocalDate.now();
        this.timeOfCreation = LocalTime.now();
        this.tags = new LinkedHashSet<>();
    }

    public BlogPost(User author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.dateOfCreation = LocalDate.now();
        this.timeOfCreation = LocalTime.now();
        this.tags = new LinkedHashSet<>();
    }

    public int getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public LocalTime getTimeOfCreation() {
        return timeOfCreation;
    }

    public LocalDateTime getTimeOfEdit() {
        return timeOfEdit;
    }

    public void setTimeOfEdit(LocalDateTime timeOfEdit) {
        this.timeOfEdit = timeOfEdit;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Comment> getComments() {
        return comments;
    }

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
