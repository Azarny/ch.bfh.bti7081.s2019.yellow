package ch.bfh.bti7081.model.seminar;

import ch.bfh.bti7081.model.manager.SeminarManager;

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



    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber){
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

    public void setDate(LocalDateTime date) throws  IllegalArgumentException {
        this.date = date;
    }

    public SeminarCategory getCategory(){
        return category;
    }

    public void setCategory(SeminarCategory category){
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) throws IllegalArgumentException{
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // one query creation of seminar possible
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

    // default constructor needed because of overload
    public Seminar(){}


}
