package ch.bfh.bti7081.model.seminar;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class Seminar {
    private String street;
    private String houseNumber;
    private Integer plz;
    private String location;
    private String title;
    private LocalDateTime date;
    private SeminarCategory category;
    private String url;
    private String description;

    private int maxYearsInFuture = 5;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) throws IllegalArgumentException {
        if (street.trim().length() < 2) throw new IllegalArgumentException("Street names have to be 2 chars at min.");
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) throws IllegalArgumentException{
        if (!houseNumber.matches("^\\d*\\w$")) throw new IllegalArgumentException("No valid house number.");
        this.houseNumber = houseNumber;
    }

    public Integer getPlz() {
        return plz;
    }

    public void setPlz(Integer plz) throws IllegalArgumentException{
        if(plz == null || !((plz > 999 && plz < 10000) || (plz > 99999 && plz < 1000000))) throw new IllegalArgumentException("No valid PLZ.");
        this.plz = plz;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (location.trim().length() < 2) throw new IllegalArgumentException("The location have to be 2 chars at min.");
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title.trim().length() < 2) throw new IllegalArgumentException("The title have to be 2 chars at min.");
        this.title = title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) throws  IllegalArgumentException {
        if (date.isBefore(LocalDateTime.now())) throw new IllegalArgumentException("Date can't be in the past.");
        if (date.isAfter(LocalDateTime.now().plusYears(maxYearsInFuture))) throw new IllegalArgumentException("Please do not enter events more than " + maxYearsInFuture + " years away.");
        this.date = date;
    }

    public SeminarCategory getCategory(){

        return category;
    }

    public void setCategory(SeminarCategory category) throws IllegalArgumentException {
        if (category == null) throw new IllegalArgumentException("Category can't be empty");
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) throws IllegalArgumentException{
        if (!url.matches("^((https?|ftp)://)?(\\w+\\.)+(\\w{2}|\\w{3})(/\\S+(\\./\\S+)*)?$")) throw new IllegalArgumentException("No valid URL.");
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description.trim().length() < 2) throw new IllegalArgumentException("The description have to be 2 chars at min.");
        this.description = description;
    }

    public Seminar(String street, String houseNumber, Integer plz, String location, String title, LocalDateTime date, SeminarCategory category, String url, String description){
            setStreet(street);
            setHouseNumber(houseNumber);
            setPlz(plz);
            setLocation(location);
            setTitle(title);
            setDate(date);
            setCategory(category);
            setUrl(url);
            setDescription(description);
    }

    public Seminar(){}


}
