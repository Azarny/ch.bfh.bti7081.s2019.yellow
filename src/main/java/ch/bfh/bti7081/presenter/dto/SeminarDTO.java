package ch.bfh.bti7081.presenter.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data-Transfer object for seminaries.
 * This class serves as boundary between model and view, which allows to transfer values differently.
 * No logic is implemented on this class.
 *
 * @author walty1
 */
public class SeminarDTO {
    private String street;
    private String houseNumber;
    //PLZ as Double as frontend knows double-number-fields.
    private Double plz;
    private String location;
    private String title;
    //Time and date are separated
    private LocalTime time;
    private LocalDate date;
    private String category;
    private String url;
    private String description;
    private double location_lat;
    private double location_lng;

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

    public Double getPlz() {
        return plz;
    }

    public void setPlz(Double plz) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public double getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(double location_lat) {
        this.location_lat = location_lat;
    }

    public double getLocation_lng() {
        return location_lng;
    }

    public void setLocation_lng(double location_lng) {
        this.location_lng = location_lng;
    }
}
