package ch.bfh.bti7081.model.seminar;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class Seminar {
    @NotBlank(message="Street name can't be empty.")
    private String street;
    @NotBlank(message="House number can't be empty.")
    private String houseNumber;
    private Integer plz;
    @NotBlank(message="Location can't be empty.")
    private String location;
    @NotBlank(message="Title can't be empty.")
    private String title;
    private LocalDateTime date;
    private SeminarCategory category;
    @NotBlank(message="URL can't be empty.")
    private String url;
    @NotBlank(message="Description can't be empty.")
    private String description;

    private int maxYearsInFuture = 5;

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
        if (!houseNumber.matches("^\\d*\\w$")) throw new IllegalArgumentException("No valid house number.");
        this.houseNumber = houseNumber;
    }

    public Integer getPlz() {
        return plz;
    }

    public void setPlz(Integer plz) {
        if(plz == null || !((plz > 999 && plz < 10000) || (plz > 99999 && plz > 1000000))) throw new IllegalArgumentException("No valid PLZ.");
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
        if (date.isBefore(LocalDateTime.now())) throw new IllegalArgumentException("Date can't be in the past.");
        if (date.isAfter(LocalDateTime.now().plusYears(maxYearsInFuture))) throw new IllegalArgumentException("Please do not enter events more than " + maxYearsInFuture + " years away.");
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
        if (!url.matches("@(https?|ftp)://(-\\.)?([^\\s/?\\.#-]+\\.?)+(/[^\\s]*)?$@iS")) throw new IllegalArgumentException("No valid URL.");
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
