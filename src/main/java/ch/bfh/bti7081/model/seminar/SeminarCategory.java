package ch.bfh.bti7081.model.seminar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author siegn2
 */
@Entity
@Table(name = "seminar_category")
public class SeminarCategory {
    private static final String PREFIX = "S_CAT_";

    @Id
    @Column(name = PREFIX + "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = PREFIX + "NAME")
    private String name;

    public SeminarCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
