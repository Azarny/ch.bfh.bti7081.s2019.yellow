package ch.bfh.bti7081.presenter.dto;


import java.time.LocalDate;

/**
 * Data-Transfer object for seminarfilters.
 * This class serves as boundary between model and view, which allows to transfer values differently.
 *
 * No logic is implemented on this class.
 *
 * @author oppls7
 */
public class SeminarFilterDTO {
    private String keyword;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String category;
    private String location;

    public String getKeyword() { return keyword; }

    public void setKeyword(String keyword) {
      this.keyword = keyword;
    }

    public LocalDate getFromDate() { return fromDate; }

    public void setFromDate(LocalDate fromDate) {
      this.fromDate = fromDate;
    }

    public LocalDate getToDate() { return toDate; }

    public void setToDate(LocalDate toDate) {
      this.toDate = toDate;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) {
      this.category = category;
    }

    public String getLocation() { return location; }

    public void setLocation(String location) {
      this.location = location;
    }
}
