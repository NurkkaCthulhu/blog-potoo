package fi.tuni.lesserpotoo.blogpotoo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fi.tuni.lesserpotoo.blogpotoo.misc.UserType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Essi Supponen [essi.supponen@tuni.fi]
 * @version 2019-0423
 * @since 3.0
 */
@Entity
@Table(name = "user", schema = "schema")
public class User {

    /**
     * Unique integer id
     */
    @Id
    @GeneratedValue(generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "USER_SEQ", allocationSize = 1)
    private int id;

    /**
     * Username of the user
     */
    @Column(nullable = false)
    private String username;

    /**
     * Password of the user.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    /**
     * User type of the user.
     */
    @Column(nullable = false)
    private UserType userType;

    /**
     * All BlogPosts the user has made.
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<BlogPost> blogPosts = new HashSet<>();

    /**
     * All comments the user has made.
     */
    @OneToMany(mappedBy = "commenter", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Comment> comments = new HashSet<>();

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Constructor with username, password and userType.
     *
     * @param username
     * @param password
     * @param userType
     */
    public User(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
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
     * Returns username.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns userType.
     *
     * @return userType
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Sets userType.
     *
     * @param userType
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    /**
     * Returns all BlogPosts the user has made.
     *
     * @return blogPosts
     */
    public Set<BlogPost> getBlogPosts() {
        return blogPosts;
    }

    /**
     * Returns all Comments the user has made.
     *
     * @return comments
     */
    public Set<Comment> getComments() {
        return comments;
    }

    /**
     * Returns string representation of the user
     *
     * @return string representation of the user
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                '}';
    }
}
