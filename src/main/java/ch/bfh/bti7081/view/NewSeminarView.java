package ch.bfh.bti7081.view;

import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Route(value = "newSeminar", layout = Layout.class)
public class NewSeminarView extends VerticalLayout {

    private H1 title = new H1("Seminar erstellen");

    private TextField seminarTitle = new TextField("Seminar-Titel");
    private Select<SeminarCategory> seminarCategory = new Select<>();

    private TimePicker seminarTime = new TimePicker("Zeit");
    private DatePicker seminarDate = new DatePicker("Datum");
    private FormLayout dateComposite = new FormLayout(seminarDate,seminarTime);

    private TextArea seminarDescription = new TextArea("Beschreibung");
    private TextField seminarLink = new TextField("Externer Link");

    private TextField seminarStreet = new TextField("Strasse");
    private TextField seminarStreetNbr = new TextField("Nr.");
    private FormLayout streetComposite = new FormLayout(seminarStreet,seminarStreetNbr);

    private NumberField seminarPlz = new NumberField("PLZ");
    private TextField seminarPlace = new TextField ("Ort");
    private FormLayout placeComposite = new FormLayout(seminarPlz, seminarPlace);

    private FormLayout seminarForm = new FormLayout();

    private Label errorMessage = new Label("Hier könnte ihre Fehlermeldung stehen.");

    private Button save = new Button("Save", new Icon(VaadinIcon.PLUS));
    private Button cancel = new Button("Cancel", new Icon(VaadinIcon.EXIT));

    private HorizontalLayout formActions = new HorizontalLayout(save,cancel);


    private Binder<Seminar> binder = new Binder<>();
    private Seminar newSeminar = new Seminar();

    public NewSeminarView(){
        seminarForm.add(seminarTitle);
        seminarForm.add(seminarCategory);

        seminarForm.add(dateComposite);
        seminarForm.add(seminarLink);
        seminarForm.add(streetComposite);
        seminarForm.add(placeComposite);

        seminarForm.add(seminarDescription);

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
        seminarLink.setRequiredIndicatorVisible(true);
        seminarDescription.setRequiredIndicatorVisible(true);

        //Category-Settings
        seminarCategory.setLabel("Kategorie");
        seminarCategory.setItems(SeminarCategoryManager.getSeminarCategories());
        seminarCategory.setTextRenderer(SeminarCategory::getName);
        seminarCategory.setItemLabelGenerator(SeminarCategory::getName);
        seminarCategory.setEmptySelectionAllowed(false);

        //Binder-Configuration
        binder.forField(seminarTitle).asRequired("Bitte Titel angeben.").
                withValidator(title -> title.trim().length() >= SeminarManager.minTitleLength, "Titel muss aus mind. "+SeminarManager.minTitleLength + " Zeichen bestehen.").
                bind(Seminar::getTitle, Seminar::setTitle);

        //DateTimeConvertation
        //withConverter(UI-Value to Model, Model-Value to UI, Error Message if not succesfull)
        //When Time or Date are saved, the other component is read to fill the datetime.
        binder.forField(seminarDate).asRequired("Bitte Datum angeben.").
                withConverter(date->{
                    LocalTime time = seminarTime.getValue();
                    if(time==null){
                        time = LocalTime.of(0,0);
                    }
                    return LocalDateTime.of(date, time);
                }, LocalDateTime::toLocalDate)
                .withValidator(seminarDate -> seminarDate.isAfter(LocalDateTime.now().minusDays(1)), "Datum ist in der Vergangenheit.")
                .withValidator(seminarDate -> seminarDate.isBefore(LocalDateTime.now().plusYears(SeminarManager.maxYearsInFuture)), "Datum ist mehr als " + SeminarManager.maxYearsInFuture + " Jahre in der Zukunft.")
                .bind(Seminar::getDate,Seminar::setDate);

        binder.forField(seminarTime).asRequired("Bitte Zeit angeben.").
                withConverter(time->{
                    LocalDate date = seminarDate.getValue();
                    return LocalDateTime.of(date, time);
                },LocalDateTime::toLocalTime).
                bind(Seminar::getDate,Seminar::setDate);

        binder.forField(seminarCategory).
                asRequired("Bitte eine Kategorie wählen.").
                bind(Seminar::getCategory, Seminar::setCategory);

        binder.forField(seminarStreet).asRequired("Bitte Strassennamen angeben.").
                withValidator(street -> street.trim().length() >= SeminarManager.minStreetLength, "Strasse muss aus mind. " + SeminarManager.minStreetLength + " Zeichen bestehen.").
                bind(Seminar::getStreet, Seminar::setStreet);

        binder.forField(seminarStreetNbr).
                asRequired("Bitte Hausnummer angeben.").
                withValidator(houseNumber -> houseNumber.matches("^\\d*\\w$"), "Hausnummer ungültig.").
                withValidator(houseNumber -> houseNumber.trim().length() >= SeminarManager.minStreetNumberLength, "Hausnummer muss aus mind. " + SeminarManager.minStreetNumberLength + "bestehen.").
                bind(Seminar::getHouseNumber, Seminar::setHouseNumber);

        binder.forField(seminarPlz).
                withConverter(Double::intValue, Integer::doubleValue,"Bitte eine PLZ eingeben").
                withValidator(plz -> ((plz > 999 && plz < 10000) || (plz > 99999 && plz < 1000000)), "Ungültige PLZ.").
                bind(Seminar::getPlz, Seminar::setPlz);

        binder.forField(seminarPlace).asRequired("Bitte Ort angeben.").
                withValidator(location -> location.length() >= SeminarManager.minLocationLength, "Ort muss aus mind. " + SeminarManager.minLocationLength +" Zeichen bestehen.").
                bind(Seminar::getLocation, Seminar::setLocation);

        binder.forField(seminarLink).
                asRequired("Bitte URL angeben.").
                withValidator(url -> url.matches("^((https?)://)?(\\w+\\.)+(\\w{2}|\\w{3})(/\\S+(\\./\\S+)*)?$"),"Ungültiger Link").
                bind(Seminar::getUrl, Seminar::setUrl);

        binder.forField(seminarDescription).
                asRequired("Bitte Beschreibung angeben").
                withValidator(description -> description.trim().length() >= SeminarManager.minDescriptionLength, "Beschreibung muss aus mind. " + SeminarManager.minDescriptionLength + " Zeichen bestehen.").
                bind(Seminar::getDescription, Seminar::setDescription);

        this.add(title);
        this.add(seminarForm);
        this.add(errorMessage);
        this.add(formActions);

        save.addClickListener(event -> {
            if (binder.writeBeanIfValid(newSeminar)) {
                SeminarManager.createSeminar(newSeminar);
                save.getUI().ifPresent(ui -> ui.navigate("seminar"));
            } else {
                BinderValidationStatus<Seminar> validate = binder.validate();
                String errorText = validate.getFieldValidationStatuses()
                        .stream().filter(BindingValidationStatus::isError)
                        .map(BindingValidationStatus::getMessage)
                        .map(Optional::get).distinct()
                        .collect(Collectors.joining(", "));
                errorMessage.setText("There are errors: " + errorText);

            }
        });

        cancel.addClickListener(event -> cancel.getUI().ifPresent(ui -> ui.navigate("seminar")));
    }
}
