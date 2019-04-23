package fi.tuni.lesserpotoo.blogpotoo;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue(generator = "comment_seq")
    @SequenceGenerator(name = "comment_seq", sequenceName = "COMMENT_SEQ", allocationSize = 1)
    int id;

    @ManyToOne
    @JoinColumn
    User commenter;

    @ManyToOne
    @JoinColumn
    BlogPost blogPost;

    @Column(nullable = false)
    LocalDate dateOfComment;

    @Column(nullable = false)
    LocalTime timeOfComment;

    @Column(nullable = false)
    String content;

    public Comment() {
        dateOfComment = LocalDate.now();
        timeOfComment = LocalTime.now();
    }

    public Comment(User commenter, BlogPost blogPost, String content) {
        this.commenter = commenter;
        this.blogPost = blogPost;
        this.content = content;
        dateOfComment = LocalDate.now();
        timeOfComment = LocalTime.now();
    }

    public LocalDate getDateOfComment() {
        return dateOfComment;
    }

    public void setDateOfComment(LocalDate dateOfComment) {
        this.dateOfComment = dateOfComment;
    }

    public LocalTime getTimeOfComment() {
        return timeOfComment;
    }

    public void setTimeOfComment(LocalTime timeOfComment) {
        this.timeOfComment = timeOfComment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public User getCommenter() {
        return commenter;
    }

    public BlogPost getBlogPost() {
        return blogPost;
    }

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
