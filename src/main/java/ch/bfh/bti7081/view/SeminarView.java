package ch.bfh.bti7081.view;

import ch.bfh.bti7081.model.dto.SeminarDTO;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import ch.bfh.bti7081.model.seminar.SeminarFilter;
import ch.bfh.bti7081.presenter.SeminarPresenter;
import ch.bfh.bti7081.view.customComponents.ErrorNotification;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Route(value = "seminar", layout = Layout.class)
public class SeminarView extends VerticalLayout {
    @Autowired
    private SeminarPresenter seminarPresenter;

    private H1 title = new H1("Seminarfinder");
    private VerticalLayout welcomeLayout = new VerticalLayout(title);
    private VerticalLayout seminarFilterLayout = new VerticalLayout();
    private VerticalLayout seminarListLayout = new VerticalLayout();
    private Dialog details = new Dialog();
    private FormLayout filterFormLayout = new FormLayout();
    private Label errorLabel = new Label();
    private Grid<SeminarDTO> seminarGrid = new Grid<>();
    private ComboBox<SeminarCategory> categoriesCb = new ComboBox<>();

    public SeminarView() {
    }

    @PostConstruct
    public void init() throws Exception {
        setViewContent(seminarPresenter.getSeminarDtos());
        generateFilterLayout();
        generateListLayout();
        VerticalLayout leftLayout = new VerticalLayout();
        VerticalLayout rightLayout = new VerticalLayout();
        HorizontalLayout contentLayout = new HorizontalLayout();
        contentLayout.add(leftLayout, rightLayout);
        leftLayout.add(seminarFilterLayout, seminarListLayout, details);
        this.add(welcomeLayout,contentLayout);
    }

    /*
     * Generates the filter-layout with Binder
     *
     * Author: oppls7
     * */
    private void generateFilterLayout() {
        Binder<SeminarFilter> binder = new Binder<>();
        SeminarFilter seminarFilter = new SeminarFilter();
        TextField searchTf = new TextField();
        searchTf.setLabel("Suchbegriff:");
        binder.forField(searchTf).bind(SeminarFilter::getKeyword, SeminarFilter::setKeyword);

        DatePicker fromDateDp = new DatePicker();
        fromDateDp.setLabel("Datum von:");
        binder.forField(fromDateDp).bind(SeminarFilter::getFromDate, SeminarFilter::setFromDate);

        DatePicker toDateDp = new DatePicker();
        toDateDp.setLabel("Datum bis:");
        binder.forField(toDateDp).bind(SeminarFilter::getToDate, SeminarFilter::setToDate);

        categoriesCb.setLabel("Kategorien:");
        categoriesCb.setItemLabelGenerator(SeminarCategory::getName);
        binder.forField(categoriesCb).bind(SeminarFilter::getCategory, SeminarFilter::setCategory);

        TextField ortTf = new TextField();
        ortTf.setLabel("Ort:");
        ortTf.setPlaceholder("Ort...");
        binder.forField(ortTf).bind(SeminarFilter::getLocation, SeminarFilter::setLocation);

        Button filterBtn = new Button("Filter anwenden", new Icon(VaadinIcon.CHECK));
        filterBtn.addClickShortcut(Key.ENTER);

        // Click listener for the filter-button
        filterBtn.addClickListener(event -> {
            if (binder.writeBeanIfValid(seminarFilter)) {
                try {
                    seminarGrid.setItems(seminarPresenter.getFilteredSeminarDtos(seminarFilter));
                } catch(Exception ex){
                    this.add(new ErrorNotification("Es ist ein technischer Fehler aufgetreten. " +
                            "Bitte versuchen Sie es später noch einmal oder wenden sie sich an den Support."));
                }
            } else {
                BinderValidationStatus<SeminarFilter> validate = binder.validate();
                String errorText = validate.getFieldValidationStatuses()
                        .stream().filter(BindingValidationStatus::isError)
                        .map(BindingValidationStatus::getMessage)
                        .map(Optional::get).distinct()
                        .collect(Collectors.joining(", "));
                this.add(new ErrorNotification("Es ist ein technischer Fehler aufgetreten: " + errorText));
            }
        });

        Button resetFilterBtn = new Button("Filter löschen", new Icon(VaadinIcon.CLOSE));
        // Click listener for the filter-button
        resetFilterBtn.addClickListener(event -> {
            // clear fields by setting null
            binder.readBean(null);
            seminarFilter.reset();
            try {
                setViewContent(seminarPresenter.getFilteredSeminarDtos(seminarFilter));
            } catch (Exception e) {
                this.add(new ErrorNotification("Es ist ein technischer Fehler aufgetreten. " +
                        "Bitte versuchen Sie es später noch einmal oder wenden sie sich an den Support."));
            }
        });
        filterFormLayout.add(searchTf, fromDateDp, toDateDp, categoriesCb, ortTf, errorLabel, resetFilterBtn, filterBtn);
        filterFormLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("21em", 2));
        seminarFilterLayout.add(filterFormLayout);
    }

    /*
     * Generates the list of seminaries.
     *
     * Author: oppls7
     * */
    private void generateListLayout() {
        Button newSeminar = new Button("Neues Seminar", new Icon(VaadinIcon.EDIT));
        newSeminar.addClickListener(event -> newSeminar.getUI().ifPresent(ui -> ui.navigate("seminar/new")));

        DateTimeFormatter dateFormatter = DateTimeFormatter
                .ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter
                .ofPattern("kk:mm");
        seminarGrid.addColumn(TemplateRenderer.<SeminarDTO>of(
                "<div style='padding:10px'><div style='font-weight:bold'>[[item.title]]<br></div>" +
                        "<div>[[item.date]] [[item.time]]<div style='float:right'>[[item.location]]" +
                        "</div></div></div>")
                .withProperty("title", SeminarDTO::getTitle)
                .withProperty("date",
                        SeminarDTO -> dateFormatter.format(SeminarDTO.getDate()))
                .withProperty("time",
                        SeminarDTO -> timeFormatter.format(SeminarDTO.getTime()))
                .withProperty("location", SeminarDTO::getLocation))
                .setFlexGrow(6);
        seminarGrid.addColumn(new ComponentRenderer<>(
                () -> new Icon(VaadinIcon.INFO_CIRCLE))).setWidth("10px");
        seminarGrid.asSingleSelect().addValueChangeListener(event -> showDetails(event.getValue()));
        seminarGrid.setHeightByRows(true);
        seminarListLayout.add(newSeminar, seminarGrid);
    }

    private void setViewContent(List<SeminarDTO> seminaries){
        try {
            seminarGrid.setItems(seminaries);
        }catch(Exception e){
            this.add(new ErrorNotification("Es ist ein technischer Fehler aufgetreten. " +
                    "Bitte versuchen Sie es später noch einmal oder wenden sie sich an den Support."));
        }
    }

    /*
     * Used for the ListItemClickEvent. It opens the dialog with seminary-details.
     *
     * Author: oppls7
     * */
    private void showDetails(SeminarDTO seminar) {
        details.removeAll();
        generateDialog(seminar);
        details.open();
    }

    /*
     * Generates a dialog, which shows the details from the clicked seminary
     *
     * Author: oppls7
     * */
    private void generateDialog(SeminarDTO seminar) {
        H3 title = new H3(seminar.getTitle());
        Span locationAndPlz = new Span("Veranstaltungsort: " + seminar.getStreet() + " " + seminar.getHouseNumber() + ", "
                + seminar.getPlz() + " " + seminar.getLocation());
        locationAndPlz.getStyle().set("display", "block");
        Span category = new Span("Kategorie: " + seminar.getCategory());
        category.getStyle().set("display", "block");
        LocalDate localDate = seminar.getDate();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formatDate = localDate.format(dateFormatter);
        Span date = new Span("Datum: "+formatDate);
        date.getStyle().set("display", "block");
        LocalTime localtime = seminar.getTime();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("kk:mm");
        String formatTime = localtime.format(timeFormatter);
        Span time = new Span("Uhrzeit: "+formatTime);
        time.getStyle().set("display", "block");
        Span link = new Span("Zum Veranstalter: " + seminar.getUrl());
        link.getStyle().set("display", "block");
        Span description = new Span("Beschreibung: " + seminar.getDescription());
        description.getStyle().set("display", "block");
        details.add(title, date,time, category, locationAndPlz, link, description);
        details.setWidth("500px");
        details.setHeight("auto");
    }

}
