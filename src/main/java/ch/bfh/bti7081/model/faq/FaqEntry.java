package ch.bfh.bti7081.model.faq;

import java.util.ArrayList;
import java.util.List;

public class FaqEntry {
    private String title;
    private String text;
    private List<FaqEntry> subEntries;

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
}
