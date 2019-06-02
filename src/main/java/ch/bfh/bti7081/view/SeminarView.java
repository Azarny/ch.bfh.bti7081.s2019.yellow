package ch.bfh.bti7081.view;

import ch.bfh.bti7081.model.dto.UserDTO;
import ch.bfh.bti7081.model.dto.SeminarDTO;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import ch.bfh.bti7081.model.seminar.SeminarFilter;
import ch.bfh.bti7081.presenter.SeminarPresenter;
import ch.bfh.bti7081.presenter.UserPresenter;
import ch.bfh.bti7081.view.customComponents.ErrorNotification;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
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
    @Autowired
    private UserPresenter userPresenter;

    private H1 title = new H1("Seminarfinder");
    private VerticalLayout welcomeLayout = new VerticalLayout(title);

    private ComboBox<SeminarCategory> categoriesCb = new ComboBox<>("Kategorie:");
    private TextField searchTf = new TextField("Suchbegriff:");
    private DatePicker fromDateDp = new DatePicker("Datum von:");
    private DatePicker toDateDp = new DatePicker("Datum bis:");
    private TextField ortTf = new TextField("Ort:","Ort eingeben:");
    private Button filterBtn = new Button("Filter anwenden", new Icon(VaadinIcon.CHECK));
    private Button resetFilterBtn = new Button("Filter löschen", new Icon(VaadinIcon.CLOSE));
    private SeminarFilter seminarFilter = new SeminarFilter();
    private Binder<SeminarFilter> binder = new Binder<>();
    private FormLayout filterFormLayout = new FormLayout(searchTf, fromDateDp, toDateDp, categoriesCb,
            ortTf, resetFilterBtn, filterBtn);

    private Button newSeminar = new Button("Neues Seminar", new Icon(VaadinIcon.EDIT));

    private Grid<SeminarDTO> seminarGrid = new Grid<>();

    private Dialog details = new Dialog();

    private VerticalLayout leftLayout = new VerticalLayout();
    //map-layout
    private VerticalLayout rightLayout = new VerticalLayout();
    private HorizontalLayout contentLayout = new HorizontalLayout(leftLayout, rightLayout);


    @PostConstruct
    public void init() throws Exception {
        setElementSettings();
        setActions();
        addBindingToForm();
        buildPage();
        setViewContent(seminarPresenter.getSeminarDtos());
        categoriesCb.setItems(seminarPresenter.getSeminarCategories());
    }

    private void buildPage() {
        // check if user is logged in
        String userName = (String) VaadinSession.getCurrent().getAttribute("userName");
        if (!((userName == null) || ("".equals(userName)))) {
            UserDTO user = userPresenter.getUserByUsername(userName);

            // check if user is expert or moderator
            if (user.getPermission() >= 2) {
                leftLayout.add(newSeminar);
            }
        }

        // finish constructing leftLayout
        leftLayout.add(filterFormLayout, seminarGrid, details);

        this.add(welcomeLayout,contentLayout);
    }

    private void addBindingToForm(){
        binder.forField(searchTf).bind(SeminarFilter::getKeyword, SeminarFilter::setKeyword);
        binder.forField(fromDateDp).bind(SeminarFilter::getFromDate, SeminarFilter::setFromDate);
        binder.forField(toDateDp).bind(SeminarFilter::getToDate, SeminarFilter::setToDate);
        binder.forField(categoriesCb).bind(SeminarFilter::getCategory, SeminarFilter::setCategory);
        binder.forField(ortTf).bind(SeminarFilter::getLocation, SeminarFilter::setLocation);
    }

    private void setElementSettings() {
        //Filter-Settings
        categoriesCb.setItemLabelGenerator(SeminarCategory::getName);
        filterBtn.addClickShortcut(Key.ENTER);
        filterFormLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("21em", 2));

        //List-Settings
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("kk:mm");
        seminarGrid.addColumn(TemplateRenderer.<SeminarDTO>of(
                "<div style='padding:10px'>" +
                        "<div style='font-weight:bold'>[[item.title]]<br></div>" +
                        "<div>[[item.date]] [[item.time]]<div style='float:right'>[[item.location]]</div></div>" +
                        "</div>")
                .withProperty("title", SeminarDTO::getTitle)
                .withProperty("date", SeminarDTO -> dateFormatter.format(SeminarDTO.getDate()))
                .withProperty("time", SeminarDTO -> timeFormatter.format(SeminarDTO.getTime()))
                .withProperty("location", SeminarDTO::getLocation))
                .setFlexGrow(6);
        seminarGrid.addColumn(new ComponentRenderer<>(() -> new Icon(VaadinIcon.INFO_CIRCLE))).setWidth("10px");
        seminarGrid.asSingleSelect().addValueChangeListener(event -> showDetails(event.getValue()));
        seminarGrid.setHeightByRows(true);
    }

    private void setActions(){
        // filter-button action
        filterBtn.addClickListener(event -> {
            if (binder.writeBeanIfValid(seminarFilter)) {
                try {
                    setViewContent(seminarPresenter.getFilteredSeminarDtos(seminarFilter));
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

        // reset-button action
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

        // new seminar action
        newSeminar.addClickListener(event -> newSeminar.getUI().ifPresent(ui -> ui.navigate("seminar/new")));
    }

    /*
     * Fills the grid with seminaries
     *
     * Author: oppls7
     * */
    private void setViewContent(List<SeminarDTO> seminaries){
        try {
            seminarGrid.setItems(seminaries);
            if(seminaries.size()==0) {
                Notification note = new Notification("Es wurden keine Seminare gefunden.",3000);
                note.open();
            }
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

        LocalTime localtime = seminar.getTime();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("kk:mm");
        String formatTime = localtime.format(timeFormatter);

        LocalDate localDate = seminar.getDate();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formatDate = localDate.format(dateFormatter);

        Span locationAndPlz = new Span("Veranstaltungsort: " + seminar.getStreet() + " " + seminar.getHouseNumber() + ", "
                + seminar.getPlz() + " " + seminar.getLocation());
        Span category = new Span("Kategorie: " + seminar.getCategory());
        Span date = new Span("Datum: "+formatDate);
        Span time = new Span("Uhrzeit: "+formatTime);
        Anchor link = new Anchor(seminar.getUrl(), "Weitere Informationen (Externer Link)");
        // opens in a new tab
        link.setTarget("_blank");
        Span description = new Span("Beschreibung: " + seminar.getDescription());

        locationAndPlz.getStyle().set("display", "block");
        time.getStyle().set("display", "block");
        date.getStyle().set("display", "block");
        category.getStyle().set("display", "block");
        link.getStyle().set("display", "block");
        description.getStyle().set("display", "block");

        details.add(title, date,time, category, locationAndPlz, link, description);
        details.setWidth("500px");
        details.setHeight("auto");
    }
}
