package ch.bfh.bti7081.model.forum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "forum_category")
public class ForumCategory {
    private final static String PREFIX = "F_CAT_";

    @Id
    @Column(name = PREFIX + "NAME")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
