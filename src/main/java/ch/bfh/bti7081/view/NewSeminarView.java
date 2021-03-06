package ch.bfh.bti7081.view;

import ch.bfh.bti7081.presenter.NewSeminarPresenter;
import ch.bfh.bti7081.presenter.UserPresenter;
import ch.bfh.bti7081.presenter.dto.SeminarDTO;
import ch.bfh.bti7081.presenter.dto.UserDTO;
import ch.bfh.bti7081.view.customComponents.ErrorNotification;
import com.google.maps.errors.NotFoundException;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@StyleSheet("styles/style.css")
@UIScope
@Component
@Route(value = "seminar/new", layout = Layout.class)
public class NewSeminarView extends VerticalLayout {
    //Error-Messages
    private static final String NOT_PERMITTED = "Sie verfügen nicht über die benötigten Berechtigungen.";
    private static final String NOT_LOGGED_IN = "Bitte melden Sie sich an.";
    @Autowired
    private NewSeminarPresenter presenter;
    @Autowired
    private UserPresenter userPresenter;
    //Upper Part of website
    private H1 title = new H1("Seminar erstellen");
    private FormLayout seminarForm = new FormLayout();
    //Form-Components
    private TextField seminarTitle = new TextField("Seminar-Titel");
    private Select<String> seminarCategory = new Select<>();
    private TextArea seminarDescription = new TextArea("Beschreibung");
    private TextField seminarUrl = new TextField("Externer Link");
    //Date-composite
    private TimePicker seminarTime = new TimePicker("Zeit");
    private DatePicker seminarDate = new DatePicker("Datum");
    private FormLayout dateComposite = new FormLayout(seminarDate, seminarTime);
    //Street composite
    private TextField seminarStreet = new TextField("Strasse");
    private TextField seminarStreetNbr = new TextField("Nr.");
    private FormLayout streetComposite = new FormLayout(seminarStreet, seminarStreetNbr);
    //Place composite
    private NumberField seminarPlz = new NumberField("PLZ");
    private TextField seminarPlace = new TextField("Ort");
    private FormLayout placeComposite = new FormLayout(seminarPlz, seminarPlace);
    //Buttons
    private Button save = new Button("Save", new Icon(VaadinIcon.PLUS));
    private Button cancel = new Button("Cancel", new Icon(VaadinIcon.EXIT));
    private HorizontalLayout formActions = new HorizontalLayout(save, cancel);

    private Binder<SeminarDTO> binder = new Binder<>();
    private SeminarDTO newSeminar = new SeminarDTO();

    /**
     * Initialises the content of the view.
     *
     * @author walty1
     * @author heuzl1
     */
    @PostConstruct
    public void init() {
        // check if user is logged in
        String userName = (String) VaadinSession.getCurrent().getAttribute("userName");
        if (!((userName == null) || ("".equals(userName)))) {
            UserDTO user = userPresenter.getUserByUsername(userName);

            // check if user is expert or moderator
            if (user.getPermission() >= 2) {
                addElementsToForm();
                setFieldSettings();
                addBindingToForm();
                buildPage();
                mvpBinding();
                fillCategoryField();
            } else {
                addErrorNotificationWithRedirect(NOT_PERMITTED);
            }
        } else {
            addErrorNotificationWithRedirect(NOT_LOGGED_IN);
        }
    }

    /**
     * Shows error notification and redirects the user to the homepage.
     *
     * @param text Text to be displayed to the user
     * @author siegn2
     */
    private void addErrorNotificationWithRedirect(String text) {
        ErrorNotification errorNotification = new ErrorNotification(text);
        errorNotification.addDetachListener(event -> errorNotification.getUI().ifPresent(ui -> ui.navigate("")));
        this.add(errorNotification);
    }

    /**
     * Prepares the form.
     *
     * @author walty1
     */
    private void addElementsToForm() {
        seminarForm.add(seminarTitle);
        seminarForm.add(seminarCategory);
        seminarForm.add(dateComposite);
        seminarForm.add(seminarUrl);
        seminarForm.add(streetComposite);
        seminarForm.add(placeComposite);
        seminarForm.add(seminarDescription);
    }

    /**
     * Sets the settings on different fields.
     *
     * @author walty1
     */
    private void setFieldSettings() {
        seminarTitle.setRequiredIndicatorVisible(true);
        seminarCategory.setRequiredIndicatorVisible(true);
        //dateTimeComposite
        seminarDate.setRequiredIndicatorVisible(true);
        seminarTime.setRequiredIndicatorVisible(true);
        //streetComposite
        seminarStreet.setRequiredIndicatorVisible(true);
        seminarStreetNbr.setRequiredIndicatorVisible(true);
        //placeComposite
        seminarPlz.setRequiredIndicatorVisible(true);
        seminarPlace.setRequiredIndicatorVisible(true);
        seminarUrl.setRequiredIndicatorVisible(true);
        seminarDescription.setRequiredIndicatorVisible(true);
        //Category-Settings
        seminarCategory.setLabel("Kategorie");
        seminarCategory.setEmptySelectionAllowed(false);
    }

    /**
     * Using binder, the frontend-validation is implemented.
     *
     * @author walty1
     * @author luscm1
     */
    private void addBindingToForm() {
        //Binder-Configuration
        binder.forField(seminarTitle).asRequired("Bitte Titel angeben")
                .withValidator(title -> title.trim().length() >= presenter.getMinTitleLength(),
                        "Titel muss aus mind. " + presenter.getMinTitleLength() + " Zeichen bestehen")
                .bind(SeminarDTO::getTitle, SeminarDTO::setTitle);
        binder.forField(seminarDate).asRequired("Bitte Datum angeben")
                .withValidator(seminarDate -> seminarDate.isAfter(LocalDateTime.now().minusDays(1).toLocalDate()),
                        "Datum ist in der Vergangenheit.")
                .withValidator(seminarDate -> seminarDate.isBefore(LocalDateTime.now()
                        .plusYears(presenter.getMaxYearsInFuture()).toLocalDate()),
                        "Datum ist mehr als " + presenter.getMaxYearsInFuture() + " Jahre in der Zukunft.")
                .bind(SeminarDTO::getDate, SeminarDTO::setDate);
        binder.forField(seminarTime).asRequired("Bitte Zeit angeben").bind(SeminarDTO::getTime, SeminarDTO::setTime);
        binder.forField(seminarCategory).asRequired("Bitte eine Kategorie wählen.").
                bind(SeminarDTO::getCategory, SeminarDTO::setCategory);
        binder.forField(seminarStreet).asRequired("Bitte Strasse angeben")
                .withValidator(street -> street.trim().length() >= presenter.getMinStreetLength(),
                        "Strasse muss aus mind. " + presenter.getMinStreetLength() + " Zeichen bestehen.")
                .bind(SeminarDTO::getStreet, SeminarDTO::setStreet);
        binder.forField(seminarStreetNbr).asRequired("Bitte Hausnummer angeben")
                .withValidator(houseNumber -> houseNumber.matches("^\\d*\\w$"), "Hausnummer ungültig.")
                .withValidator(houseNumber -> houseNumber.trim().length() >= presenter.getMinStreetNumberLength(),
                        "Hausnummer muss aus mind. " + presenter.getMinStreetNumberLength() + "bestehen.")
                .bind(SeminarDTO::getHouseNumber, SeminarDTO::setHouseNumber);
        binder.forField(seminarPlz).asRequired("Bitte PLZ angeben")
                .withValidator(plz -> ((plz > 999 && plz < 10000) || (plz > 99999 && plz < 1000000)),
                        "Ungültige PLZ.")
                .bind(SeminarDTO::getPlz, SeminarDTO::setPlz);
        binder.forField(seminarPlace).asRequired("Bitte Ort angeben")
                .withValidator(location -> location.length() >= presenter.getMinLocationLength(),
                        "Ort muss aus mind. " + presenter.getMinLocationLength() + " Zeichen bestehen.")
                .bind(SeminarDTO::getLocation, SeminarDTO::setLocation);
        binder.forField(seminarUrl).asRequired("Bitte URL angeben")
                .withValidator(url -> url.matches("^((https?)://)?(\\w+\\.)+(\\w{2}|\\w{3})(/\\S+(\\./\\S+)*)?$"),
                        "Ungültiger Link")
                .bind(SeminarDTO::getUrl, SeminarDTO::setUrl);
        binder.forField(seminarDescription).asRequired("Bitte Beschreibung angeben")
                .withValidator(description -> description.trim().length() >= presenter.getMinDescriptionLength(),
                        "Beschreibung muss aus mind. " + presenter.getMinDescriptionLength() + " Zeichen bestehen.")
                .bind(SeminarDTO::getDescription, SeminarDTO::setDescription);
    }


    /**
     * After all objects have been prepared, they are added to the view. -> Displaying starts.
     *
     * @author walty1
     */
    private void buildPage() {
        this.add(title);
        this.add(seminarForm);
        this.add(formActions);
    }

    /**
     * Adds a connection between presenter and view.
     *
     * @author walty1
     */
    private void mvpBinding() {
        setFormActions();
    }

    /**
     * Configuration for the buttons on the view.
     *
     * @author walty1
     */
    private void setFormActions() {
        save.addClickListener(event -> {
            if (binder.writeBeanIfValid(newSeminar)) {
                try {
                    presenter.sendSeminarToBackend(newSeminar);
                    save.getUI().ifPresent(ui -> ui.navigate("seminar"));
                } catch (IllegalArgumentException | NotFoundException | IllegalStateException e) {
                    displayErrorMessage(e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    displayErrorMessage("Es ist ein technischer Fehler aufgetreten. " +
                            "Bitte versuchen Sie es später noch einmal oder wenden sie sich an den Support.");
                }
            }
        });

        cancel.addClickListener(event -> cancel.getUI().ifPresent(ui -> ui.navigate("seminar")));
    }

    /**
     * The view gets the categories from the presenter.
     *
     * @author walty1
     */
    private void fillCategoryField() {
        List<String> seminarCategories = presenter.getSeminarCategories();
        seminarCategory.setItems(seminarCategories);
    }

    /**
     * Displays an error-message to the user.
     *
     * @author walty1
     */
    private void displayErrorMessage(String message) {
        this.add(new ErrorNotification(message));
    }
}
