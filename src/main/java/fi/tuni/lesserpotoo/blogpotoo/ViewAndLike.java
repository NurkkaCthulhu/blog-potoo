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
}
