package ch.bfh.bti7081.model.seminar;

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
@Table(name = "seminar")
public class Seminar {
    private static final String PREFIX = "SEM_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = PREFIX + "ID")
    private Long id;

    @Column(name = PREFIX + "STREET", length = 100)
    private String street;

    @Column(name = PREFIX + "HOUSE_NUMBER", length = 10)
    private String houseNumber;

    @Column(name = PREFIX + "PLZ")
    private Integer plz;

    @Column(name = PREFIX + "LOCATION", length = 100)
    private String location;

    @Column(name = PREFIX + "TITLE", length = 50)
    private String title;

    @Column(name = PREFIX + "DATE")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = PREFIX + "CATEGORY")
    private SeminarCategory category;

    @Column(name = PREFIX + "URL", length = 255)
    private String url;

    @Column(name = PREFIX + "DESCRIPTION", length = 255)
    private String description;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Integer getPlz() {
        return plz;
    }

    public void setPlz(Integer plz) {
        this.plz = plz;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public SeminarCategory getCategory() {
        return category;
    }

    public void setCategory(SeminarCategory category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
