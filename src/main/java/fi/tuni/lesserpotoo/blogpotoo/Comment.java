package fi.tuni.lesserpotoo.blogpotoo;

import jdk.vm.ci.meta.Local;

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
    User commentor;

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

    public Comment(User commentor, BlogPost blogPost, String content) {
        this.commentor = commentor;
        this.blogPost = blogPost;
        this.content = content;
        dateOfComment = LocalDate.now();
        timeOfComment = LocalTime.now();
    }
}
