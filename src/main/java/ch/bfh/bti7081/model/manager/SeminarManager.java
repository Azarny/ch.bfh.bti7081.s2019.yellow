package ch.bfh.bti7081.model.manager;

import ch.bfh.bti7081.model.ValidationConstants;
import ch.bfh.bti7081.model.repositories.SeminarRepository;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SeminarManager {
    // manages the communication between backend and frontend
    // the list of methods isn't completed yet, just some sample methods for the class diagramm

    @Autowired
    private SeminarRepository seminarRepository;

    public List<Seminar> getSeminaries() {
        return seminarRepository.findAll();
    }

    /*
     * Returns filtered seminaries from db
     *
     * Author: luscm1
     * */
    public List<Seminar> getFilteredSeminars(SeminarFilter filter) {
        return getSeminaries().stream()
                //filter category
                .filter(seminar -> {
                    if (filter.getCategory() == null) {
                        return true;
                    } else {
                        return seminar.getCategory().getName().toLowerCase().equals(filter.getCategory()
                                .getName().toLowerCase());
                    }
                })
                //filter location
                .filter(seminar -> {
                    if (filter.getLocation() == null) {
                        return true;
                    } else {
                        return seminar.getLocation().toLowerCase().contains(filter.getLocation().toLowerCase());
                    }
                })
                //filter date
                .filter(seminar -> {
                    if (filter.getToDate() == null) {
                        return true;
                    } else {
                        return seminar.getDate().isBefore(filter.getToDate().atStartOfDay().plusDays(1)
                                .minusSeconds(1));
                    }
                })
                .filter(seminar -> {
                    if (filter.getFromDate() == null) {
                        return true;
                    } else {
                        return seminar.getDate().isAfter(filter.getFromDate().atStartOfDay());
                    }
                })
                // filter keyword
                .filter(seminar -> {
                    if (filter.getKeyword() == null) {
                        return true;
                    } else {
                        String[] keywords = filter.getKeyword().split(" ");
                        int matchedKeywordsCount = 0;
                        for (String keyword : keywords) {
                            if (seminar.getTitle().toLowerCase().contains(keyword.toLowerCase())
                                            || seminar.getDescription().toLowerCase().contains(keyword.toLowerCase())){
                                matchedKeywordsCount++;
                            }
                        }
                        // compare matched keywords with total keywords
                        return matchedKeywordsCount == keywords.length;
                    }
                })
                .collect(Collectors.toList());

    }


    public void createSeminar(Seminar seminar) {
        seminarRepository.save(seminar);
    }

    /*
     * validates if the seminar fulfills all the requirements
     *
     * Author: luscm1
     * */
    public String validateSeminar(Seminar seminar) {
        String returnString = "";

        if (seminar.getStreet() == null || seminar.getStreet().trim().length() <
                ValidationConstants.MIN_STREET_LENGTH.value) {
            returnString += "no valid street name, ";
        }

        if (seminar.getHouseNumber() == null || !seminar.getHouseNumber().matches("^\\d*\\w$") ||
                seminar.getHouseNumber().trim().length() <
                        ValidationConstants.MIN_STREETNUMBER_LENGTH.value) {
            returnString += "no valid house number, ";
        }

        if (seminar.getPlz() == null || !((seminar.getPlz() > 999 && seminar.getPlz() < 10000) ||
                (seminar.getPlz() > 99999 && seminar.getPlz() < 1000000))) {
            returnString += "no valid PLZ, ";
        }

        if (seminar.getLocation() == null || seminar.getLocation().trim().length() <
                ValidationConstants.MIN_LOCATION_LENGTH.value) {
            returnString += "no valid location, ";
        }

        if (seminar.getTitle() == null || seminar.getTitle().trim().length() <
                ValidationConstants.MIN_TITLE_LENGTH.value) {
            returnString += "no valid title, ";
        }

        if (seminar.getDate() == null || seminar.getDate().isBefore(LocalDateTime.now()) || seminar.getDate()
                .isAfter(LocalDateTime.now().plusYears(ValidationConstants.MAX_YEARS_IN_FUTURE.value))) {
            returnString += "no valid date, ";
        }

        if (seminar.getCategory() == null) {
            returnString += "no valid category, ";
        }

        //regex pattern description:
        //^((https?|ftp)://)? --> allows http://, https:// and nothing
        // (\w+\.)+(\w{2}|\w{3}) --> allows url with one or more "parts" before the .topleveldomain.
        // top level domains are made of 2 or 3 chars
        // (/\S+(\./\S+)*)?$ --> allows all the stuff after the last / but no whitespaces
        if (seminar.getUrl() == null || !seminar.getUrl().
                matches("^((https?)://)?(\\w+\\.)+(\\w{2}|\\w{3})(/\\S+(\\./\\S+)*)?$")) {
            returnString += "no valid URL, ";
        }

        if (seminar.getDescription() == null || seminar.getDescription().trim().length() <
                ValidationConstants.MIN_DESCRIPTION_LENGTH.value) {
            returnString += "no valid description, ";
        }

        return returnString;
    }

    private LocalDateTime dateGenerator(String timeToParse) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(timeToParse, formatter);
    }


    public void deleteSeminar(Seminar seminar) {
        seminarRepository.delete(seminar);
    }


}
