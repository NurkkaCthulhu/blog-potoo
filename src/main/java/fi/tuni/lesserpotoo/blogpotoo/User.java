package fi.tuni.lesserpotoo.blogpotoo;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "USER_SEQ", allocationSize = 1)
    int id;

    @Column(nullable = false)
    String userName;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    UserType userType;
}
