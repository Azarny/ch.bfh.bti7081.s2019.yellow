package ch.bfh.bti7081.presenter.dto;

import ch.bfh.bti7081.model.forum.ForumEntry;
import ch.bfh.bti7081.model.forum.ForumEntryComment;
import ch.bfh.bti7081.model.manager.UserManager;
import ch.bfh.bti7081.presenter.UserPresenter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserDTO {

    @Autowired
    UserManager usermanager;

    @Autowired
    UserPresenter userPresenter;

    private String username;
    private String email;
    private String password;
    private Integer permission;
    private String salt;
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

    public void setClearPassword(String password) throws Exception {
        this.password = userPresenter.encryptPassword(getUsername(), password);
    }

    public void setEncryptedPassword(String password){
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
