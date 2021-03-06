package ch.bfh.bti7081.model.seminar;

import java.time.LocalDate;

/**
 * Filter-object for the frontend.
 *
 * @author luscm1
 */
public class SeminarFilter {

    private String keyword;
    private LocalDate fromDate;
    private LocalDate toDate;
    private SeminarCategory category;
    private String location;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public SeminarCategory getCategory() {
        return category;
    }

    public void setCategory(SeminarCategory category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
