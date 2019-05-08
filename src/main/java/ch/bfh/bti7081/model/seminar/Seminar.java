package ch.bfh.bti7081.model.seminar;

import java.time.LocalDateTime;

public class Seminar {
    private String street;
    private String houseNumber;
    private Integer plz;
    private String location;
    private String title;
    private LocalDateTime date;
    private SeminarCategory category;
    private String link;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean validate(String street, String houseNumber, String plz, String location, String title, String date, String category, String link, String description){
        // array contains values [value][RegEx pattern] for each field
        String[][] validationSet = {
            {street,""},
            {houseNumber,""},
            {plz,""},
            {location,""},
            {title,""},
            {date,""},
            {category,""},
            {link,""},
            {description,""}
        };

        for (String[] set: validationSet) {
            if(true){
                return false;
            }
        }
        return true;

    }
}
