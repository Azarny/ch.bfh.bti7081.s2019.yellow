package ch.bfh.bti7081.model.forum;

import java.time.LocalDate;

public class ForumEntryComment {
    private String text;
    private String author;
    private LocalDate creationDate;
    private boolean marked;
    private ForumEntry responseTo;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public ForumEntry getResponseTo() {
        return responseTo;
    }

    public void setResponseTo(ForumEntry responseTo) {
        this.responseTo = responseTo;
    }
}
