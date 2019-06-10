package ch.bfh.bti7081.view;

import ch.bfh.bti7081.presenter.dto.SeminarFilterDTO;
import ch.bfh.bti7081.presenter.dto.UserDTO;
import ch.bfh.bti7081.presenter.dto.SeminarDTO;
import ch.bfh.bti7081.presenter.SeminarPresenter;
import ch.bfh.bti7081.presenter.UserPresenter;
import ch.bfh.bti7081.view.customComponents.ErrorNotification;
import ch.bfh.bti7081.view.customComponents.GoogleMap;
import ch.bfh.bti7081.view.customComponents.GoogleMapMarker;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.Div;
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
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Route(value = "seminar", layout = Layout.class)
public class SeminarView extends VerticalLayout {
    @Autowired
    private SeminarPresenter seminarPresenter;
    @Autowired
    private UserPresenter userPresenter;
    @Value("${healthApp.googleApiKey:NOKEYFOUND}")
    private String googleApiKey;
    //Upper Part of website
    private H1 title = new H1("Seminarfinder");
    //Components for filter
    private ComboBox<String> categoriesCb = new ComboBox<>("Kategorie:");
    private TextField searchTf = new TextField("Suchbegriff:");
    private DatePicker fromDateDp = new DatePicker("Datum von:");
    private DatePicker toDateDp = new DatePicker("Datum bis:");
    private TextField ortTf = new TextField("Ort:", "Ort eingeben:");
    private Button filterBtn = new Button("Filter anwenden", new Icon(VaadinIcon.CHECK));
    private Button resetFilterBtn = new Button("Filter löschen", new Icon(VaadinIcon.CLOSE));
    private SeminarFilterDTO seminarFilter = new SeminarFilterDTO();
    private Binder<SeminarFilterDTO> binder = new Binder<>();
    private FormLayout filterFormLayout = new FormLayout(
            searchTf, fromDateDp, toDateDp, categoriesCb, ortTf, filterBtn, resetFilterBtn);
    private Details filterDetails = new Details("Filter",new Div());
    //New-Seminar-Button
    private Button newSeminar = new Button("Neues Seminar", new Icon(VaadinIcon.EDIT));
    //Seminar-Components
    private Grid<SeminarDTO> seminarGrid = new Grid<>();
    private Dialog details = new Dialog();
    private Button closeDetails = new Button("", new Icon(VaadinIcon.CLOSE));
    //Seminar-Map and settings for having Switzerland focused. (Standard.)
    private GoogleMap seminarMap = new GoogleMap();
    private List<SeminarDTO> mapSeminaries;
    private double STANDARDLAT = 46.798;
    private double STANDARDLNG = 8.231;
    private int STANDARDZOOM = 8;
    private VerticalLayout leftLayout = new VerticalLayout();
    private VerticalLayout topLayout = new VerticalLayout(title);
    private VerticalLayout rightLayout = new VerticalLayout(seminarMap);
    private HorizontalLayout contentLayout = new HorizontalLayout(leftLayout, rightLayout);

    /**
     * Initializes the creation of the view after it has been constructed by Spring.
     *
     * @author oppls7
     */
    @PostConstruct
    public void init() {
        setElementSettings();
        setActions();
        addBindingToForm();
        buildPage();
        setViewContent(seminarPresenter.getSeminarDtos());
        categoriesCb.setItems(seminarPresenter.getSeminarCategories());
    }

    /**
     * Using this method, the page is built. --> Elements are shown on the client.
     *
     * @author oppls7
     */
    private void buildPage() {
        // check if user is logged in
        String userName = (String) VaadinSession.getCurrent().getAttribute("userName");
        if (!((userName == null) || ("".equals(userName)))) {
            UserDTO user = userPresenter.getUserByUsername(userName);
            // check if user is expert or moderator
            if (user.getPermission() >= 2) {
                topLayout.add(newSeminar);
            }
        }
        // finish constructing layouts
        leftLayout.add(seminarGrid);
        filterDetails.addContent(filterFormLayout);
        topLayout.add(filterDetails);
        this.add(topLayout, contentLayout, details);
    }

    /**
     * The filter on the frontend is bound to a SeminarFilter object.
     *
     * @author oppls7
     */
    private void addBindingToForm() {
        binder.forField(searchTf).bind(SeminarFilterDTO::getKeyword, SeminarFilterDTO::setKeyword);
        binder.forField(fromDateDp).bind(SeminarFilterDTO::getFromDate, SeminarFilterDTO::setFromDate);
        binder.forField(toDateDp).bind(SeminarFilterDTO::getToDate, SeminarFilterDTO::setToDate);
        binder.forField(categoriesCb).bind(SeminarFilterDTO::getCategory, SeminarFilterDTO::setCategory);
        binder.forField(ortTf).bind(SeminarFilterDTO::getLocation, SeminarFilterDTO::setLocation);
    }

    /**
     * Sets different settings for the components.
     *
     * @author oppls7
     * @author walty1
     */
    private void setElementSettings() {
        //Layout-Settings
        contentLayout.setId("content");
        rightLayout.setId("right-layout");
        //Filter-Settings
        filterBtn.addClickShortcut(Key.ENTER);
        filterFormLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("21em", 2),
                new FormLayout.ResponsiveStep("21em", 3),
                new FormLayout.ResponsiveStep("21em", 4),
                new FormLayout.ResponsiveStep("21em", 5));
        //List-Settings
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("kk:mm");
        seminarGrid.addColumn(TemplateRenderer.<SeminarDTO>of("<div style='padding:10px'>" +
                        "<div style='font-weight:bold'>[[item.title]]<br></div>" +
                        "<div>[[item.date]] [[item.time]]<div style='float:right'>[[item.location]]</div></div>" +
                        "</div>")
                .withProperty("title", SeminarDTO::getTitle)
                .withProperty("date", SeminarDTO -> dateFormatter.format(SeminarDTO.getDate()))
                .withProperty("time", SeminarDTO -> timeFormatter.format(SeminarDTO.getTime()))
                .withProperty("location", SeminarDTO::getLocation))
                .setFlexGrow(6);
        seminarGrid.addColumn(new ComponentRenderer<>(() -> new Icon(VaadinIcon.INFO_CIRCLE))).setWidth("10px");
        seminarGrid.setHeightByRows(true);

        contentLayout.getStyle().set("width", "100%");
        details.setCloseOnEsc(false);
        details.setCloseOnOutsideClick(true);

        closeDetails.setId("close-details");

        //Map-Settings
        seminarMap.setApiKey(googleApiKey);
        seminarMap.setLatitude(STANDARDLAT);
        seminarMap.setLongitude(STANDARDLNG);
        seminarMap.setZoomLevel(STANDARDZOOM);
        seminarMap.addMapReadyListener(event -> {
            setSeminarMarkers(mapSeminaries);
        });
        // set the locale for the date format
        toDateDp.setLocale(Locale.GERMANY);
        fromDateDp.setLocale(Locale.GERMANY);
    }

    /**
     * Configuration for the buttons on the view.
     *
     * @author oppls7
     */
    private void setActions() {
        seminarGrid.asSingleSelect();
        seminarGrid.addSelectionListener(event -> {
            if(event.getFirstSelectedItem().isPresent()) showDetails(event.getFirstSelectedItem().get());
        });
        details.addDialogCloseActionListener(event -> {
            seminarGrid.deselectAll();
            seminarGrid.select(null);
        });
        // filter-button action
        filterBtn.addClickListener(event -> {
            if (binder.writeBeanIfValid(seminarFilter)) {
                try {
                    setViewContent(seminarPresenter.getFilteredSeminarDtos(seminarFilter));
                } catch (Exception ex) {
                    this.add(new ErrorNotification("Es ist ein technischer Fehler aufgetreten. " +
                            "Bitte versuchen Sie es später noch einmal oder wenden sie sich an den Support."));
                }
            } else {
                BinderValidationStatus<SeminarFilterDTO> validate = binder.validate();
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
            seminarFilter = seminarPresenter.reset(seminarFilter);
            try {
                setViewContent(seminarPresenter.getFilteredSeminarDtos(seminarFilter));
            } catch (Exception e) {
                this.add(new ErrorNotification("Es ist ein technischer Fehler aufgetreten. " +
                        "Bitte versuchen Sie es später noch einmal oder wenden sie sich an den Support."));
            }
        });

        // new seminar action
        newSeminar.addClickListener(event -> {
            newSeminar.getUI().ifPresent(ui -> ui.navigate("seminar/new"));
        });

        // close details action
        closeDetails.addClickListener(event -> {
            details.close();
            seminarGrid.select(null);
            seminarGrid.deselectAll();
        });
    }

    /**
     * Fills the grid with seminaries and adds them also to the map.
     *
     * @author oppls7
     * @author walty1
     */
    private void setViewContent(List<SeminarDTO> seminaries) {
        try {
            seminarGrid.setItems(seminaries);
            if (seminaries.size() == 0) {
                Notification note = new Notification("Es wurden keine Seminare gefunden.", 3000);
                note.open();
            }
            setSeminarMarkers(seminaries);
        } catch (Exception e) {
            this.add(new ErrorNotification("Es ist ein technischer Fehler aufgetreten. " +
                    "Bitte versuchen Sie es später noch einmal oder wenden sie sich an den Support."));
        }
    }

    /**
     * For every seminary in the list, the seminarMap gets a marker.
     * The map only will show the seminaries in the list.
     * A click on a seminar marker opens the detail-box.
     *
     * @param seminaries (List of all active seminaries.)
     * @author walty1
     */
    private void setSeminarMarkers(List<SeminarDTO> seminaries) {
        if (!seminarMap.isMapReady()) {
            //If the map is not ready, we memorize the seminaries for later display.
            //See listener that is set in setElementSettings
            mapSeminaries = seminaries;
        } else {
            seminarMap.resetMarkers();
            for (SeminarDTO seminar : seminaries) {
                GoogleMapMarker seminarMarker = new GoogleMapMarker(
                        seminar.getLocation_lat(),
                        seminar.getLocation_lng());
                seminarMarker.setTitle(seminar.getTitle());
                seminarMarker.setDraggable(false);
                seminarMarker.addClickListener(event -> showDetails(seminar));
                seminarMap.addMarker(seminarMarker);
            }
            //Set the new center of the map.
            //As every seminar in our database must have lat and lng, no exception handling is necessary.
            if (seminaries.size() > 0) {
                double mapCenterLat = seminaries.stream().
                        mapToDouble(SeminarDTO::getLocation_lat).
                        average().
                        getAsDouble();
                double mapCenterLng = seminaries.stream().
                        mapToDouble(SeminarDTO::getLocation_lng).
                        average().
                        getAsDouble();
                seminarMap.setLatitude(mapCenterLat);
                seminarMap.setLongitude(mapCenterLng);
            } else {
                seminarMap.setLongitude(STANDARDLNG);
                seminarMap.setLatitude(STANDARDLAT);
                seminarMap.setZoomLevel(STANDARDZOOM);
            }
        }
    }

    /**
     * Used for the ListItemClickEvent. It opens the dialog with seminary-details.
     *
     * @author oppls7
     */
    private void showDetails(SeminarDTO seminar) {
        details.removeAll();
        generateDialog(seminar);
        details.open();
    }

    /**
     * Generates a dialog, which shows the details from the clicked seminary
     *
     * @param seminar DTO of the seminar to be visible in the pop-up
     * @author oppls7
     * @author siegn2
     * */
    private void generateDialog(SeminarDTO seminar) {
        Div content = new Div();
        String seminarTitle = seminar.getTitle();
        H3 title = new H3(seminarTitle);

        LocalTime localtime = seminar.getTime();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("kk:mm");
        String formatTime = localtime.format(timeFormatter);

        LocalDate localDate = seminar.getDate();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formatDate = localDate.format(dateFormatter);

        Div locationLabel = new Div(new Span("Veranstaltungsort:"));
        locationLabel.setClassName("detail-label");
        Div locationAndPlz = new Div(new Span(seminar.getStreet() + " " + seminar.getHouseNumber() +
                ", " + seminar.getPlz().intValue() + " " + seminar.getLocation()));
        locationAndPlz.setClassName("detail-wert");
        Div locationDiv = new Div(locationLabel, locationAndPlz);
        locationDiv.setClassName("detail-div");

        Div categoryLabel = new Div(new Span("Kategorie:"));
        categoryLabel.setClassName("detail-label");
        Div category = new Div(new Span(seminar.getCategory()));
        category.setClassName("detail-wert");
        Div categoryDiv = new Div(categoryLabel, category);
        categoryDiv.setClassName("detail-div");

        Div dateLabel = new Div(new Span("Termin:"));
        dateLabel.setClassName("detail-label");
        Div dateTime = new Div(new Span(formatDate + ", " + formatTime + " Uhr"));
        dateTime.setClassName("detail-wert");
        Div dateDiv = new Div(dateLabel, dateTime);
        dateDiv.setClassName("detail-div");

        Div linkLabel = new Div(new Span("Link zum Veranstalter:"));
        linkLabel.setClassName("detail-label");
        Anchor linkAnchor = new Anchor(seminar.getUrl(), seminar.getUrl());
        // opens in a new tab
        linkAnchor.setTarget("_blank");
        Div link = new Div(linkAnchor);
        link.setClassName("detail-wert");
        Div linkDiv = new Div(linkLabel, link);
        linkDiv.setClassName("detail-div");

        Div descriptionLabel = new Div(new Span("Beschreibung:"));
        descriptionLabel.setClassName("detail-label");
        Div description = new Div(new Span(seminar.getDescription()));
        description.setClassName("detail-wert");
        Div descriptionDiv = new Div(descriptionLabel, description);
        descriptionDiv.setClassName("detail-div");
        Div last = new Div();
        last.setClassName("last");

        content.add(title, dateDiv, locationDiv, categoryDiv, linkDiv, descriptionDiv, last, closeDetails);
        details.add(content);
        content.setId("details");
    }

}
