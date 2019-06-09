package ch.bfh.bti7081.presenter.dto;

import ch.bfh.bti7081.model.User;
import ch.bfh.bti7081.model.forum.ForumCategory;
import ch.bfh.bti7081.model.forum.ForumEntryComment;

import java.time.LocalDate;
import java.util.List;

/**
 * Data-Transfer object for forum entries.
 * This class serves as boundary between model and view, which allows to transfer values differently.
 * No logic is implemented on this class.
 *
 * @author luscm1
 */
public class ForumEntryDTO {
    private String title;
    private String text;
    private User author;
    private LocalDate creationDate;
    private ForumCategory category;
    private List<ForumEntryComment> comments;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public ForumCategory getCategory() {
        return category;
    }

    public void setCategory(ForumCategory category) {
        this.category = category;
    }

    public List<ForumEntryComment> getComments() {
        return comments;
    }

    public void setComments(List<ForumEntryComment> comments) {
        this.comments = comments;
    }
}
