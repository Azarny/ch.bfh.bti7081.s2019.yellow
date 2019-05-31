package ch.bfh.bti7081.model;

import ch.bfh.bti7081.model.forum.ForumEntry;
import ch.bfh.bti7081.model.forum.ForumEntryComment;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    private static final String PREFIX = "USER_";

    @Id
    @Column(name=PREFIX + "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = PREFIX + "USERNAME", length = 20)
    private String username;

    @Column(name = PREFIX + "EMAIL", length = 255)
    private String email;

    @Column(name = PREFIX + "PASSWORD", length = 255)
    private String password;

    @Column(name = PREFIX + "PERMISSION")
    private Integer permission;

    @Column(name = PREFIX + "SALT", length = 512)
    private String salt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "author")
    private List<ForumEntry> forumEntries;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "author")
    private List<ForumEntryComment> comments;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    public List<ForumEntry> getForumEntries() {
        return forumEntries;
    }

    public void setForumEntries(List<ForumEntry> forumEntries) {
        this.forumEntries = forumEntries;
    }

    public List<ForumEntryComment> getComments() {
        return comments;
    }

    public void setComments(List<ForumEntryComment> comments) {
        this.comments = comments;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
