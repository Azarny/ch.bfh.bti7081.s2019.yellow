package ch.bfh.bti7081.model.forum;

import ch.bfh.bti7081.model.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "forum_entry")
public class ForumEntry {
    private static final String PREFIX = "F_ENTRY_";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = PREFIX + "ID")
    private Long id;

    @Column(name = PREFIX + "TITLE", length = 50)
    private String title;

    @Column(name = PREFIX + "TEXT", length = 255)
    private String text;

    @ManyToOne
    @JoinColumn(name = PREFIX + "AUTHOR")
    private User author;

    @Column(name = PREFIX + "CREATION_DATE")
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = PREFIX + "CATEGORY")
    private ForumCategory category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "responseTo")
    private List<ForumEntryComment> comments;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
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
