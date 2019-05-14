package ch.bfh.bti7081.model.seminar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "seminar_category")
public class SeminarCategory {
    private static final String PREFIX = "S_CAT_";

    @Id
    @Column(name = PREFIX + "NAME")
    private String name;

    public String getName() {
        return name;
    }

    public SeminarCategory(String name){
        this.name=name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
