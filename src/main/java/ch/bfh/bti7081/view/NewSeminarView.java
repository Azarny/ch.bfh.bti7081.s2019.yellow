package ch.bfh.bti7081.view;

import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.util.Optional;
import java.util.stream.Collectors;

@Route(value = "newSeminar", layout = Layout.class)
public class NewSeminarView extends FormLayout {
    private H1 title = new H1("Seminar erstellen");

    //FormLayout layoutWithBinder = new FormLayout();
    Binder<Seminar> binder = new Binder<>();

    // The object that will be edited
    Seminar newSeminar = new Seminar();

    // Create the fields
    private TextField seminarTitle = new TextField("Seminar-Titel");
    private DatePicker seminarDate = new DatePicker("Zeitpunkt");
    private Label infoLabel = new Label("Keine Info");

    private NativeButton save = new NativeButton("Save");
    private NativeButton reset = new NativeButton("Reset");



    public NewSeminarView(){
        seminarTitle.setRequiredIndicatorVisible(true);
        seminarDate.setRequiredIndicatorVisible(true);

        binder.bind(seminarTitle, Seminar::getTitle, Seminar::setTitle);

        //binder.bind(seminarDate, Seminar::getDate, Seminar::setDate);

        this.add(title);
        this.add(seminarTitle);
        this.add(seminarDate);
        this.add(save);
        this.add(reset);

        // Click listeners for the buttons
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
                infoLabel.setText("There are errors: " + errorText);
            }
        });
        reset.addClickListener(event -> {
            // clear fields by setting null
            binder.readBean(null);
            infoLabel.setText("");
        });
    }
}
