package ch.bfh.bti7081.model.dto;

import ch.bfh.bti7081.model.forum.ForumEntry;
import ch.bfh.bti7081.model.forum.ForumEntryComment;

import java.util.List;

public class UserDTO {

    private Long Id;
    private String username;
    private String email;
    private String password;
    private Integer permission;
    private List<ForumEntry> forumEntries;
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
}
