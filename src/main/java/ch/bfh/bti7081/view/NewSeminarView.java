package ch.bfh.bti7081.view;

import ch.bfh.bti7081.presenter.NewSeminarPresenter;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
    private Select<SeminarCategory> seminarCategory = new Select<SeminarCategory>();

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
        NewSeminarViewImpl view = new NewSeminarViewImpl();
        NewSeminarPresenter presenter = new NewSeminarPresenter(view);
        view.setPresenter(presenter);

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
        binder.bind(seminarTitle, Seminar::getTitle, Seminar::setTitle);

        //DateTimeConvertation
        //withConverter(UI-Value to Model, Model-Value to UI, Error Message if not succesfull)
        //When Time or Date are saved, the other component is read to fill the datetime.
        binder.forField(seminarDate).withConverter(date->{
            LocalTime time = seminarTime.getValue();
            if(time==null){
                time = LocalTime.of(0,0);
            }
            return LocalDateTime.of(date, time);
        }, LocalDateTime::toLocalDate).
                bind(Seminar::getDate,Seminar::setDate);

        binder.forField(seminarTime).withConverter(time->{
            LocalDate date = seminarDate.getValue();
            return LocalDateTime.of(date, time);
        },LocalDateTime::toLocalTime).
                bind(Seminar::getDate,Seminar::setDate);

        binder.forField(seminarCategory).asRequired("Bitte eine Kategorie wählen.").
                bind(Seminar::getCategory, Seminar::setCategory);
        binder.bind(seminarStreet, Seminar::getStreet, Seminar::setStreet);
        binder.bind(seminarStreetNbr, Seminar::getHouseNumber, Seminar::setHouseNumber);
        binder.forField(seminarPlz).
                withConverter(Double::intValue, Integer::doubleValue,"Bitte eine PLZ eingeben").
                bind(Seminar::getPlz, Seminar::setPlz);
        binder.bind(seminarPlace, Seminar::getLocation, Seminar::setLocation);
        binder.bind(seminarLink, Seminar::getLink, Seminar::setLink);
        binder.bind(seminarDescription, Seminar::getDescription, Seminar::setDescription);

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

        cancel.addClickListener(event -> {
            cancel.getUI().ifPresent(ui -> ui.navigate("seminar"));
        });
    }
}
