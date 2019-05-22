package ch.bfh.bti7081.view;

import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import ch.bfh.bti7081.model.seminar.SeminarFilter;
import ch.bfh.bti7081.presenter.SeminarPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Route(value = "seminar", layout = Layout.class)
@Component
public class SeminarView extends VerticalLayout {
    @Autowired
    private SeminarPresenter seminarPresenter;

    private SeminarFilter seminarFilter = new SeminarFilter();

    // title
    private H1 title = new H1("Seminarfinder");
    private VerticalLayout titleLayout = new VerticalLayout(title);

    // filter
    private Binder<SeminarFilter> binder = new Binder<>();
    private TextField searchTf = new TextField("Suchbegriff:");
    private DatePicker fromDateDp = new DatePicker("Datum von:");
    //private DatePicker toDateDp = new DatePicker("Datum bis:");
    private ComboBox<SeminarCategory> categoriesCb = new ComboBox<>("Kategorien:");
    private TextField ortTf = new TextField("Ort:");
    private Button filterBtn = new Button("Filter anwenden");
    private FormLayout filterLayout = new FormLayout(searchTf, /*fromDateDp, toDateDp,*/ categoriesCb, ortTf, filterBtn);

    // map
    private H2 mapTitle = new H2("Karte");
    private VerticalLayout mapLayout = new VerticalLayout(mapTitle);

    // list
    private Grid<Seminar> seminarGrid = new Grid<>();
    private VerticalLayout listLayout = new VerticalLayout(seminarGrid);

    // private
    private HorizontalLayout componentLayout = new HorizontalLayout(filterLayout, mapLayout);

    @PostConstruct
    public void init() {
        this.add(titleLayout);

        // get information from presenter
        categoriesCb.setItems(seminarPresenter.getSeminarCategories());
        seminarGrid.setItems(seminarPresenter.getSeminaries());

        // filterlayout binder
        binder.forField(searchTf).bind(SeminarFilter::getKeyword, SeminarFilter::setKeyword);
        //binder.forField(fromDateDp).bind(SeminarFilter::getFromDate, SeminarFilter::setFromDate);
        //binder.forField(toDateDp).bind(SeminarFilter::getToDate, SeminarFilter::setToDate);
        categoriesCb.setItemLabelGenerator(SeminarCategory::getName);
        binder.forField(categoriesCb).bind(SeminarFilter::getCategory, SeminarFilter::setCategory);
        ortTf.setPlaceholder("Ort...");
        binder.forField(ortTf).bind(SeminarFilter::getLocation, SeminarFilter::setLocation);
        // Click listener for the filter-button
        filterBtn.addClickListener(event -> {
            if (binder.writeBeanIfValid(seminarFilter)) {
                seminarGrid.setItems(seminarPresenter.getFilteredSeminaries(seminarFilter));
            } else {
                BinderValidationStatus<SeminarFilter> validate = binder.validate();
                String errorText = validate.getFieldValidationStatuses()
                        .stream().filter(BindingValidationStatus::isError)
                        .map(BindingValidationStatus::getMessage)
                        .map(Optional::get).distinct()
                        .collect(Collectors.joining(", "));
                ortTf.setErrorMessage("There are errors: " + errorText);
//                filterLayout.add("There are errors: " + errorText);
            }
        });
        // display fields in filter in two rows
        filterLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("21em", 2)
        );


        this.add(componentLayout);
        this.add(listLayout);
    }

    public SeminarView() {

    }
}
