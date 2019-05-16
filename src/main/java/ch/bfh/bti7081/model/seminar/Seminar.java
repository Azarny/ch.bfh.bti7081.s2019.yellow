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

    public boolean setHouseNumber(String houseNumber){
        // a house number can only have one char after the digits
        if (houseNumber == null || !houseNumber.matches("^\\d*\\w$") || houseNumber.trim().length() < SeminarManager.minStreetNumberLength) return false;
        this.houseNumber = houseNumber;
        return true;
    }

    public Integer getPlz() {
        return plz;
    }

    public boolean setPlz(Integer plz) {
        // there are plz that have 6 digits
        if(plz == null || !((plz > 999 && plz < 10000) || (plz > 99999 && plz < 1000000))) return false;
        this.plz = plz;
        return true;
    }

    public String getLocation() {
        return location;
    }

    public boolean setLocation(String location) {
        if (location == null || location.trim().length() < SeminarManager.minLocationLength) return false;
        this.location = location;
        return true;
    }

    public String getTitle() {
        return title;
    }

    public boolean setTitle(String title) {
        if ( title == null || title.trim().length() < SeminarManager.minTitleLength) return false;
        this.title = title;
        return true;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean setDate(LocalDateTime date) throws  IllegalArgumentException {
        if (date == null || date.isBefore(LocalDateTime.now()) || date.isAfter(LocalDateTime.now().plusYears(SeminarManager.maxYearsInFuture))) return false;
        this.date = date;
        return true;
    }

    public SeminarCategory getCategory(){
        return category;
    }

    public boolean setCategory(SeminarCategory category){
        if (category == null) return false;
        this.category = category;
        return true;
    }

    public String getUrl() {
        return url;
    }

    public boolean setUrl(String url) throws IllegalArgumentException{
        //regex pattern description:
        //^((https?|ftp)://)? --> allows http://, https:// and nothing
        // (\w+\.)+(\w{2}|\w{3}) --> allows url with one or more "parts" before the .topleveldomain. top level domains are made of 2 or 3 chars
        // (/\S+(\./\S+)*)?$ --> allows all the stuff after the last / but no whitespaces
        if (url == null || !url.matches("^((https?)://)?(\\w+\\.)+(\\w{2}|\\w{3})(/\\S+(\\./\\S+)*)?$")) return false;
        this.url = url;
        return true;
    }

    public String getDescription() {
        return description;
    }

    public boolean setDescription(String description) {
        if (description == null || description.trim().length() < SeminarManager.minDescriptionLength) return false;
        this.description = description;
        return true;
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
