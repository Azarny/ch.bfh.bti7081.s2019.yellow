package ch.bfh.bti7081.model.forum;

import ch.bfh.bti7081.model.User;

import java.time.LocalDate;

public class ForumEntry {
    private Integer id;
    private String title;
    private String text;
    private User author;
    private LocalDate creationDate;
    private ForumCategory category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
