package ch.bfh.bti7081.model.forum;

import ch.bfh.bti7081.model.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author siegn2
 */
@Entity
@Table(name = "forum_entry_comment")
public class ForumEntryComment {
    private static final String PREFIX = "F_ENTRY_COM";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = PREFIX + "ID")
    private Long id;

    @Column(name = PREFIX + "TEXT", length = 300)
    private String text;

    @ManyToOne
    @JoinColumn(name = PREFIX + "AUTHOR")
    private User author;

    @Column(name = PREFIX + "CREATION_DATE")
    private LocalDateTime creationDate;

    @Column(name = PREFIX + "MARKED")
    private boolean marked;

    @ManyToOne
    @JoinColumn(name = PREFIX + "QUESTION")
    private ForumEntry responseTo;

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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
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
