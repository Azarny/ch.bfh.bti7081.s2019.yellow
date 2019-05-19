package ch.bfh.bti7081.view;

import ch.bfh.bti7081.model.dto.SeminarDTO;
import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.presenter.NewSeminarPresenter;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Route(value = "seminar/new", layout = Layout.class)
public class NewSeminarView extends VerticalLayout {

    private H1 title = new H1("Seminar erstellen");
    private FormLayout seminarForm = new FormLayout();

    private TextField seminarTitle = new TextField("Seminar-Titel");
    private Select<String> seminarCategory = new Select<>();

    private TimePicker seminarTime = new TimePicker("Zeit");
    private DatePicker seminarDate = new DatePicker("Datum");
    private FormLayout dateComposite = new FormLayout(seminarDate, seminarTime);

    private TextArea seminarDescription = new TextArea("Beschreibung");
    private TextField seminarLink = new TextField("Externer Link");

    private TextField seminarStreet = new TextField("Strasse");
    private TextField seminarStreetNbr = new TextField("Nr.");
    private FormLayout streetComposite = new FormLayout(seminarStreet, seminarStreetNbr);

    private NumberField seminarPlz = new NumberField("PLZ");
    private TextField seminarPlace = new TextField("Ort");
    private FormLayout placeComposite = new FormLayout(seminarPlz, seminarPlace);


    private Label errorMessage = new Label("Hier könnte ihre Fehlermeldung stehen.");

    private Button save = new Button("Save", new Icon(VaadinIcon.PLUS));
    private Button cancel = new Button("Cancel", new Icon(VaadinIcon.EXIT));

    private HorizontalLayout formActions = new HorizontalLayout(save, cancel);

    private Binder<SeminarDTO> binder = new Binder<>();
    private SeminarDTO newSeminar = new SeminarDTO();
    private NewSeminarPresenter presenter;

    public NewSeminarView() {

        addElementsToForm();
        setFieldSettings();
        addBindingToForm();
        buildPage();
        mvpBinding(new SeminarManager(),new SeminarCategoryManager());
    }

    private void mvpBinding(SeminarManager seminarManager, SeminarCategoryManager seminarCategoryManager) {
        presenter = new NewSeminarPresenter(this, seminarManager, seminarCategoryManager);
        setFormActions();
    }

    private void addElementsToForm() {
        seminarForm.add(seminarTitle);
        seminarForm.add(seminarCategory);
        seminarForm.add(dateComposite);
        seminarForm.add(seminarLink);
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
        seminarLink.setRequiredIndicatorVisible(true);
        seminarDescription.setRequiredIndicatorVisible(true);

        //Category-Settings
        seminarCategory.setLabel("Kategorie");
        seminarCategory.setEmptySelectionAllowed(false);
    }

    private void addBindingToForm() {
        //Binder-Configuration
        binder.bind(seminarTitle, SeminarDTO::getTitle, SeminarDTO::setTitle);
        binder.bind(seminarDate, SeminarDTO::getDate, SeminarDTO::setDate);
        binder.bind(seminarTime, SeminarDTO::getTime, SeminarDTO::setTime);
        binder.forField(seminarCategory).asRequired("Bitte eine Kategorie wählen.").
                bind(SeminarDTO::getCategory, SeminarDTO::setCategory);
        binder.bind(seminarStreet, SeminarDTO::getStreet, SeminarDTO::setStreet);
        binder.bind(seminarStreetNbr, SeminarDTO::getHouseNumber, SeminarDTO::setHouseNumber);
        binder.bind(seminarPlz, SeminarDTO::getPlz, SeminarDTO::setPlz);
        binder.bind(seminarPlace, SeminarDTO::getLocation, SeminarDTO::setLocation);
        binder.bind(seminarLink, SeminarDTO::getUrl, SeminarDTO::setUrl);
        binder.bind(seminarDescription, SeminarDTO::getDescription, SeminarDTO::setDescription);
    }

    private void setFormActions() {
        save.addClickListener(event -> {
            if (binder.writeBeanIfValid(newSeminar)) {
                try {
                    presenter.sendSeminarToBackend(newSeminar);
                    save.getUI().ifPresent(ui -> ui.navigate("seminar"));
                } catch (Exception e) {
                    errorMessage.setText("Beim Speichern scheint ein Fehler aufgetreten zu sein" + e);
                }
            } else {
                BinderValidationStatus<SeminarDTO> validate = binder.validate();
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

    private void buildPage() {
        this.add(title);
        this.add(seminarForm);
        this.add(errorMessage);
        this.add(formActions);
    }

    public void setCategories(List<String> seminarCategories) {
        seminarCategory.setItems(seminarCategories);
    }
}
