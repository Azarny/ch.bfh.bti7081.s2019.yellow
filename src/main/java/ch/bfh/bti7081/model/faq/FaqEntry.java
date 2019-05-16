package ch.bfh.bti7081.model.faq;

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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "faq_entry")
public class FaqEntry {
    private static final String PREFIX = "FAQ_";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = PREFIX + "ID")
    private Long id;

    @Column(name = PREFIX + "TITLE", length = 50)
    private String title;

    @Column(name = PREFIX + "TEXT", length = 1000)
    private String text;

    @ManyToOne
    @JoinColumn(name = PREFIX + "PARENT")
    private FaqEntry parent;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parent")
    private List<FaqEntry> subEntries;

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

    public List<FaqEntry> getSubEntries() {
        return subEntries;
    }

    public void setSubEntries(List<FaqEntry> subEntries) {
        this.subEntries = subEntries;
    }

    public List<FaqEntry> addSubEntry(FaqEntry subEntry) {
        if (this.subEntries == null) {
            this.subEntries = new ArrayList<>();
        }
        this.subEntries.add(subEntry);
        return this.subEntries;
    }

    public FaqEntry getParent() {
        return parent;
    }

    public void setParent(FaqEntry parent) {
        this.parent = parent;
    }
}
