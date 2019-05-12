package ch.bfh.bti7081.view;

import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import ch.bfh.bti7081.view.single_components.LocalDateTimePicker;
import com.vaadin.flow.component.button.Button;
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
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.router.Route;

import java.util.Optional;
import java.util.stream.Collectors;

@Route(value = "newSeminar", layout = Layout.class)
public class NewSeminarView extends VerticalLayout {

    private H1 title = new H1("Seminar erstellen");

    private TextField seminarTitle = new TextField("Seminar-Titel");
    private Select<SeminarCategory> seminarCategory = new Select<SeminarCategory>();
    private LocalDateTimePicker seminarDateTime = new LocalDateTimePicker("Zeitpunkt");
    private TextArea seminarDescription = new TextArea("Beschreibung");
    private TextField seminarLink = new TextField("Externer Link");

    private TextField seminarStreet = new TextField("Strasse");
    private TextField seminarStreetNbr = new TextField("Nr.");
    private FormLayout streetComposite = new FormLayout(seminarStreet,seminarStreetNbr);

    private NumberField seminarPlz = new NumberField ("PLZ");
    private TextField seminarPlace = new TextField ("Ort");
    private FormLayout placeComposite = new FormLayout(seminarPlace,seminarPlz);

    private FormLayout seminarForm = new FormLayout();


    private Label errorMessage = new Label("Hier könnte ihre Fehlermeldung stehen.");

    //private NativeButton save = new NativeButton("Save");
    private Button save = new Button("Save", new Icon(VaadinIcon.PLUS));
    //private NativeButton reset = new NativeButton("Reset");
    private Button reset = new Button("Reset", new Icon(VaadinIcon.ERASER));
    private HorizontalLayout formActions = new HorizontalLayout(save,reset);


    private Binder<Seminar> binder = new Binder<>();
    private Seminar newSeminar = new Seminar();
    public NewSeminarView(){
        seminarForm.add(seminarTitle);
        seminarForm.add(seminarCategory);
        seminarForm.add(streetComposite);
        seminarForm.add(placeComposite);
        seminarForm.add(seminarLink);
        seminarForm.add(seminarDescription);

        seminarTitle.setRequiredIndicatorVisible(true);
        seminarCategory.setRequiredIndicatorVisible(true);
        seminarDescription.setRequiredIndicatorVisible(true);
        seminarLink.setRequiredIndicatorVisible(true);
        seminarDateTime.setRequiredIndicatorVisible(true);
        seminarPlz.setRequiredIndicatorVisible(true);
        seminarStreet.setRequiredIndicatorVisible(true);
        seminarPlace.setRequiredIndicatorVisible(true);
        seminarStreetNbr.setRequiredIndicatorVisible(true);

        seminarCategory.setLabel("Kategorie");
        seminarCategory.setItems(SeminarCategoryManager.getSeminarCategories());
        seminarCategory.setTextRenderer(SeminarCategory::getName);
        seminarCategory.setItemLabelGenerator(SeminarCategory::getName);

        binder.bind(seminarTitle, Seminar::getTitle, Seminar::setTitle);
        binder.bind(seminarDateTime, Seminar::getDate, Seminar::setDate);
        binder.bind(seminarCategory, Seminar::getCategory, Seminar::setCategory);
        binder.bind(seminarDescription, Seminar::getDescription, Seminar::setDescription);
        binder.bind(seminarStreet, Seminar::getStreet, Seminar::setStreet);
        binder.bind(seminarStreetNbr, Seminar::getHouseNumber, Seminar::setHouseNumber);

        //binder.bind(seminarPlz, s->s.getPlz().doubleValue(), Seminar::setPlz);



        this.add(title);
        this.add(seminarForm);
        this.add(errorMessage);
        this.add(formActions);

        save.addClickListener(event -> {
            if (binder.writeBeanIfValid(newSeminar)) {
                SeminarManager.createSeminar(newSeminar);
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
        reset.addClickListener(event -> {
            binder.readBean(null);
            errorMessage.setText("");
        });
    }
}
