package fi.tuni.lesserpotoo.blogpotoo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(CompositeKey.class)
public class ViewAndLike {
    @Id
    private int userId;

    @Id
    private int blogPostId;

    @Column(nullable = false)
    private boolean viewed;

    @Column(nullable = false)
    private boolean liked;

    public ViewAndLike() {
        this.viewed = false;
        this.liked = false;
    }

    public ViewAndLike(int userId, int blogPostId, boolean viewed, boolean liked) {
        this.userId = userId;
        this.blogPostId = blogPostId;
        this.viewed = viewed;
        this.liked = liked;
    }
}
