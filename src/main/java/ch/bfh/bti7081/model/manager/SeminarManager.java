package ch.bfh.bti7081.model.manager;

import ch.bfh.bti7081.model.ValidationConstants;
import ch.bfh.bti7081.model.repositories.SeminarRepository;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author siegn2
 */
@Controller
public class SeminarManager {

    @Autowired
    private SeminarRepository seminarRepository;

    /**
     * Gets all seminaries from DB that take place after today, sorted by date
     *
     * @return list of seminaries
     * @author siegn2
     */
    public List<Seminar> getSeminaries() {
        List<Seminar> seminaries = seminarRepository.findByDateGreaterThanEqual(LocalDateTime.now());
        return seminaries.stream()
                .sorted(Comparator.nullsLast(Comparator.comparing(Seminar::getDate)))
                .collect(Collectors.toList());
    }

    /**
     * Returns filtered seminaries from db
     *
     * @param filter Filters for the seminaries
     * @return List of filtered and sorted seminaries
     * @author luscm1
     * @author siegn2
     */
    public List<Seminar> getFilteredSeminars(SeminarFilter filter) {
        List<Seminar> listToFilter;

        LocalDate from = filter.getFromDate();
        LocalDate to = filter.getToDate();
        if (from != null && to != null) {
            // get all seminaries that take place in the given time period
            listToFilter = seminarRepository.getAllBetweenDates(from.atStartOfDay(), to.atStartOfDay());
        } else if (from != null) {
            // get all seminaries that take place after the given date
            listToFilter = seminarRepository.findByDateGreaterThanEqual(from.atStartOfDay());
        } else if (to != null) {
            // get all seminaries that is before the given date
            listToFilter = seminarRepository.findByDateLessThanEqual(to.atStartOfDay());
        } else {
            // if we have no date-filter active, we just get all seminaries in the future,
            // and stream-filter them here
            listToFilter = getSeminaries();
        }

        // filter the list with the other filters
        return listToFilter.stream()
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
                                    || seminar.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                                matchedKeywordsCount++;
                            }
                        }
                        // compare matched keywords with total keywords
                        return matchedKeywordsCount == keywords.length;
                    }
                })
                // order the seminaries according to their date
                .sorted(Comparator.nullsLast(Comparator.comparing(Seminar::getDate)))
                .collect(Collectors.toList());
    }

    /**
     * Saves a seminar in the database.
     *
     * @param seminar seminar with informations
     * @throws IllegalArgumentException Aborts if validation-errors occur.
     * @author luscm1
     */
    public void createSeminar(Seminar seminar) throws IllegalArgumentException {
        String validationResult = validateSeminar(seminar);
        if (validationResult.isEmpty()) {
            seminarRepository.save(seminar);
        } else {
            throw new IllegalArgumentException(validationResult);
        }
    }

    /**
     * validates if the seminar fulfills all the requirements
     *
     * @param seminar seminarobject with new information
     * @return a string containing the result of the validation (which errors were found).
     * @author luscm1
     */
    public String validateSeminar(Seminar seminar) {
        HashSet<String> errorMessages = new HashSet<>();

        if (seminar.getStreet() == null || seminar.getStreet().trim().length() <
                ValidationConstants.MIN_STREET_LENGTH.value) {
            errorMessages.add("bitte gültiger Strassenname angeben");
        }

        if (seminar.getHouseNumber() == null || !seminar.getHouseNumber().matches("^\\d*\\w$") ||
                seminar.getHouseNumber().trim().length() <
                        ValidationConstants.MIN_STREETNUMBER_LENGTH.value) {
            errorMessages.add("bitte gültige Strassennummer angeben");
        }

        if (seminar.getPlz() == null || !((seminar.getPlz() > 999 && seminar.getPlz() < 10000) ||
                (seminar.getPlz() > 99999 && seminar.getPlz() < 1000000))) {
            errorMessages.add("bitte gültige PLZ angeben");
        }

        if (seminar.getLocation() == null || seminar.getLocation().trim().length() <
                ValidationConstants.MIN_LOCATION_LENGTH.value) {
            errorMessages.add("bitte gültiger Ort angeben");
        }

        if (seminar.getTitle() == null || seminar.getTitle().trim().length() <
                ValidationConstants.MIN_TITLE_LENGTH.value) {
            errorMessages.add("bitte gültiger Titel angeben");
        }

        if (seminar.getDate() == null || seminar.getDate().isBefore(LocalDateTime.now()) || seminar.getDate()
                .isAfter(LocalDateTime.now().plusYears(ValidationConstants.MAX_YEARS_IN_FUTURE.value))) {
            errorMessages.add("bitte gültige Datum/Zeit Kombination angeben (nur zukünftige Seminare sind erlaubt)");
        }

        if (seminar.getCategory() == null) {
            errorMessages.add("bitte gültige Kategorie angeben");
        }

        //regex pattern description:
        //^((https?|ftp)://)? --> allows http://, https:// and nothing
        // (\w+\.)+(\w{2}|\w{3}) --> allows url with one or more "parts" before the .topleveldomain.
        // top level domains are made of 2 or 3 chars
        // (/\S+(\./\S+)*)?$ --> allows all the stuff after the last / but no whitespaces
        if (seminar.getUrl() == null || !seminar.getUrl().
                matches("^((https?)://)?(\\w+\\.)+(\\w{2}|\\w{3})(/\\S+(\\./\\S+)*)?$")) {
            errorMessages.add("bitte gültiger Link angeben");
        }

        if (seminar.getDescription() == null || seminar.getDescription().trim().length() <
                ValidationConstants.MIN_DESCRIPTION_LENGTH.value) {
            errorMessages.add("bitte gültige Beschreibung angeben");
        }

        String result;
        if (errorMessages.isEmpty()){
            result = "";
        }else{
            result = String.join(", ", errorMessages);
        }
        return result;
    }

    public void deleteSeminar(Seminar seminar) {
        seminarRepository.delete(seminar);
    }
}
