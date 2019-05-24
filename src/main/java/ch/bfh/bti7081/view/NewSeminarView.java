package ch.bfh.bti7081.view;

import ch.bfh.bti7081.model.dto.SeminarDTO;
import ch.bfh.bti7081.presenter.NewSeminarPresenter;
import ch.bfh.bti7081.view.customComponents.ErrorMessage;
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
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@StyleSheet("styles/style.css")
@UIScope
@Component
@Route(value = "seminar/new", layout = Layout.class)
public class NewSeminarView extends VerticalLayout {
    @Autowired
    private NewSeminarPresenter presenter;

    private H1 title = new H1("Seminar erstellen");
    private FormLayout seminarForm = new FormLayout();

    private TextField seminarTitle = new TextField("Seminar-Titel");
    private Select<String> seminarCategory = new Select<>();

    private TimePicker seminarTime = new TimePicker("Zeit");
    private DatePicker seminarDate = new DatePicker("Datum");
    private FormLayout dateComposite = new FormLayout(seminarDate, seminarTime);

    private TextArea seminarDescription = new TextArea("Beschreibung");
    private TextField seminarUrl = new TextField("Externer Link");

    private TextField seminarStreet = new TextField("Strasse");
    private TextField seminarStreetNbr = new TextField("Nr.");
    private FormLayout streetComposite = new FormLayout(seminarStreet, seminarStreetNbr);

    private NumberField seminarPlz = new NumberField("PLZ");
    private TextField seminarPlace = new TextField("Ort");
    private FormLayout placeComposite = new FormLayout(seminarPlz, seminarPlace);

    private Button save = new Button("Save", new Icon(VaadinIcon.PLUS));
    private Button cancel = new Button("Cancel", new Icon(VaadinIcon.EXIT));

    private HorizontalLayout formActions = new HorizontalLayout(save, cancel);

    private Binder<SeminarDTO> binder = new Binder<>();
    private SeminarDTO newSeminar = new SeminarDTO();

    @PostConstruct
    public void init() {
        addElementsToForm();
        setFieldSettings();
        addBindingToForm();
        buildPage();
        mvpBinding();
        fillCategoryField();
    }

    private void mvpBinding() {
        presenter.setView(this);
        setFormActions();
    }

    private void buildPage() {
        this.add(title);
        this.add(seminarForm);
        this.add(formActions);
    }

    private void addElementsToForm() {
        seminarForm.add(seminarTitle);
        seminarForm.add(seminarCategory);
        seminarForm.add(dateComposite);
        seminarForm.add(seminarUrl);
        seminarForm.add(streetComposite);
        seminarForm.add(placeComposite);
        seminarForm.add(seminarDescription);
    }

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

    private void addBindingToForm() {
        //Binder-Configuration
        binder.forField(seminarTitle).asRequired("Bitte Titel angeben")
                .withValidator(title -> title.trim().length() >= presenter.getMinTitleLength(), "Titel muss aus mind. " + presenter.getMinTitleLength() + " Zeichen bestehen")
                .bind(SeminarDTO::getTitle, SeminarDTO::setTitle);
        binder.forField(seminarDate).asRequired("Bitte Datum angeben")
                .withValidator(seminarDate -> seminarDate.isAfter(LocalDateTime.now().minusDays(1).toLocalDate()), "Datum ist in der Vergangenheit.")
                .withValidator(seminarDate -> seminarDate.isBefore(LocalDateTime.now().plusYears(presenter.getMaxYearsInFuture()).toLocalDate()), "Datum ist mehr als " + presenter.getMaxYearsInFuture() + " Jahre in der Zukunft.")
                .bind(SeminarDTO::getDate, SeminarDTO::setDate);
        binder.forField(seminarTime).asRequired("Bitte Zeit angeben").bind(SeminarDTO::getTime, SeminarDTO::setTime);
        binder.forField(seminarCategory).asRequired("Bitte eine Kategorie wählen.").
                bind(SeminarDTO::getCategory, SeminarDTO::setCategory);
        binder.forField(seminarStreet).asRequired("Bitte Strasse angeben")
                .withValidator(street -> street.trim().length() >= presenter.getMinStreetLength(), "Strasse muss aus mind. " + presenter.getMinStreetLength() + " Zeichen bestehen.")
                .bind(SeminarDTO::getStreet, SeminarDTO::setStreet);
        binder.forField(seminarStreetNbr).asRequired("Bitte Hausnummer angeben")
                .withValidator(houseNumber -> houseNumber.matches("^\\d*\\w$"), "Hausnummer ungültig.")
                .withValidator(houseNumber -> houseNumber.trim().length() >= presenter.getMinStreetNumberLength(), "Hausnummer muss aus mind. " + presenter.getMinStreetNumberLength() + "bestehen.")
                .bind(SeminarDTO::getHouseNumber, SeminarDTO::setHouseNumber);
        binder.forField(seminarPlz).asRequired("Bitte PLZ angeben")
                .withValidator(plz -> ((plz > 999 && plz < 10000) || (plz > 99999 && plz < 1000000)), "Ungültige PLZ.")
                .bind(SeminarDTO::getPlz, SeminarDTO::setPlz);
        binder.forField(seminarPlace).asRequired("Bitte Ort angeben")
                .withValidator(location -> location.length() >= presenter.getMinLocationLength(), "Ort muss aus mind. " + presenter.getMinLocationLength() + " Zeichen bestehen.")
                .bind(SeminarDTO::getLocation, SeminarDTO::setLocation);
        binder.forField(seminarUrl).asRequired("Bitte URL angeben")
                .withValidator(url -> url.matches("^((https?)://)?(\\w+\\.)+(\\w{2}|\\w{3})(/\\S+(\\./\\S+)*)?$"), "Ungültiger Link")
                .bind(SeminarDTO::getUrl, SeminarDTO::setUrl);
        binder.forField(seminarDescription).asRequired("Bitte Beschreibung angeben")
                .withValidator(description -> description.trim().length() >= presenter.getMinDescriptionLength(), "Beschreibung muss aus mind. " + presenter.getMinDescriptionLength() + " Zeichen bestehen.")
                .bind(SeminarDTO::getDescription, SeminarDTO::setDescription);
    }

    private void setFormActions() {
        save.addClickListener(event -> {
            if (binder.writeBeanIfValid(newSeminar)) {
                try {
                    presenter.sendSeminarToBackend(newSeminar);
                    save.getUI().ifPresent(ui -> ui.navigate("seminar"));
                } catch (Exception e) {
                    this.add(new ErrorMessage("Es ist ein Fehler aufgetreten. Bitte wenden Sie sich an die Administratoren."));
                }
            } else {
                BinderValidationStatus<SeminarDTO> validate = binder.validate();
                String errorText = validate.getFieldValidationStatuses()
                        .stream().filter(BindingValidationStatus::isError)
                        .map(BindingValidationStatus::getMessage)
                        .map(Optional::get).distinct()
                        .collect(Collectors.joining(", "));
                this.add(new ErrorMessage(errorText));
            }
        });

        cancel.addClickListener(event -> {
            cancel.getUI().ifPresent(ui -> ui.navigate("seminar"));
        });
    }

    public void fillCategoryField() {
        List<String> seminarCategories = presenter.getSeminarCategories();
        seminarCategory.setItems(seminarCategories);
    }
}
