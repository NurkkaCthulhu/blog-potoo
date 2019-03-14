package fi.tuni.lesserpotoo.blogpotoo;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class BlogPost {
    int id;
    String author;
    String title;
    String content;
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
}
