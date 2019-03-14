package fi.tuni.lesserpotoo.blogpotoo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BlogPost {

    @Id
    @GeneratedValue(generator = "blogpost_seq")
    @SequenceGenerator(name = "blogpost_seq", sequenceName = "BLOGPOST_SEQ", allocationSize = 1)
    int id;

    @Column(nullable = false)
    String author;
    @Column(nullable = false)
    String title;
    @Column(length=15000)
    String content;

    @Column(nullable = false)
    LocalDateTime timeOfCreation;
    LocalDateTime timeOfEdit;

    int likes;

    public BlogPost() {
    }

    public BlogPost(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.timeOfCreation = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
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

    public LocalDateTime getTimeOfCreation() {
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

    @Override
    public String toString() {
        return "BlogPost{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", timeOfCreation=" + timeOfCreation +
                ", timeOfEdit=" + timeOfEdit +
                ", likes=" + likes +
                '}';
    }
}
