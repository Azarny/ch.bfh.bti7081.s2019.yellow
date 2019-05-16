package ch.bfh.bti7081.view;

import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import ch.bfh.bti7081.model.seminar.SeminarFilter;
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
import com.vaadin.flow.data.renderer.TemplateRenderer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SeminarViewImpl extends VerticalLayout {

  private VerticalLayout SeminarFilterLayout = new VerticalLayout();
  private VerticalLayout SeminarListLayout = new VerticalLayout();
  private Dialog details = new Dialog();
  private FormLayout filterLayout = new FormLayout();
  private Grid<Seminar> seminarGrid = new Grid<>();
  private ComboBox<SeminarCategory> categoriesCb = new ComboBox<>();

  public SeminarViewImpl(){
    generateFilterLayout();
    generateListLayout();
    VerticalLayout leftLayout = new VerticalLayout();
    HorizontalLayout contentLayout = new HorizontalLayout();
    VerticalLayout rightLayout = new VerticalLayout();
    contentLayout.add(leftLayout, rightLayout);
    Button newSeminar = new Button("Neues Seminar", new Icon(VaadinIcon.EDIT));
      newSeminar.addClickListener(event -> {
        newSeminar.getUI().ifPresent(ui -> ui.navigate("newSeminar"));
      });

    leftLayout.add(SeminarFilterLayout,newSeminar,SeminarListLayout, details);
    H1 title = new H1("Seminarfinder");
    this.add(title, contentLayout);
  }

  /*
  * Generates the filter-layout with Binder
  *
  * Author: oppls7
  * */
  private void generateFilterLayout(){
    Binder<SeminarFilter> binder = new Binder<>();
    SeminarFilter seminarFilter = new SeminarFilter();
    TextField searchTf = new TextField();
    searchTf.setLabel("Suchebegriff:");
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

    Button filterBtn = new Button("Filter anwenden");

    // Click listeners for the buttons
    filterBtn.addClickListener(event -> {
      if (binder.writeBeanIfValid(seminarFilter)) {
        setFilter(seminarFilter);
      } else {
        BinderValidationStatus<SeminarFilter> validate = binder.validate();
        String errorText = validate.getFieldValidationStatuses()
                .stream().filter(BindingValidationStatus::isError)
                .map(BindingValidationStatus::getMessage)
                .map(Optional::get).distinct()
                .collect(Collectors.joining(", "));
        filterLayout.add("There are errors: " + errorText);
      }
    });

    filterLayout.add(searchTf, fromDateDp, toDateDp, categoriesCb, ortTf,filterBtn);
    filterLayout.setResponsiveSteps(
            new FormLayout.ResponsiveStep("0", 1),
            new FormLayout.ResponsiveStep("21em", 2));
    SeminarFilterLayout.add(filterLayout);
  }
  //TODO: get a new list<seminar> with seminarFilter (Binder)
  private void setFilter(SeminarFilter seminarFilter) {

  }

  /*
   * Used to fill the Combobox with Seminar Categories for using as a filter
   *
   * Author: oppls7
   * */
  public void setSeminarCategoryList(List<SeminarCategory> seminarCategoryList){
    categoriesCb.setItems(seminarCategoryList);
  }

  /*
  * Generates the list of seminaries.
  *
  * Author: oppls7
  * */
  private void generateListLayout(){
    DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd.MM.yyyy");
    seminarGrid.addColumn(TemplateRenderer.<Seminar> of(
            "<div style='padding:10px'><div style='font-weight:bold'>[[item.title]]<br></div>"+
                    "<div>[[item.date]] <div style='float:right'>[[item.location]]</div></div></div>")
            .withProperty("title", Seminar::getTitle)
            .withProperty("date",
                    seminar -> formatter.format(seminar.getDate()))
            .withProperty("location", Seminar::getLocation))
            .setFlexGrow(6);
    seminarGrid.setSelectionMode(Grid.SelectionMode.NONE);
    seminarGrid.addItemClickListener(
            event -> showDetails(event.getItem()));
    SeminarListLayout.add(seminarGrid);
  }

  /*
  * Used for the ListItemClickEvent. It opens the dialog with seminary-details.
  *
  * Author: oppls7
  * */
  private void showDetails(Seminar seminar){
    details.removeAll();
    generateDialog(seminar);
    details.open();
  }

  /*
  * Generates a dialog, which shows the details from the clicked seminary
  *
  * Author: oppls7
  * */
  private void generateDialog(Seminar seminar){
    H3 title = new H3(seminar.getTitle());
    Span locationAndPlz = new Span("Veranstaltungsort: "+seminar.getStreet() + " "+seminar.getHouseNumber()+", "+seminar.getPlz() + " "+ seminar.getLocation());
    locationAndPlz.getStyle().set("display","block");
    Span category = new Span("Kategorie: " + seminar.getCategory().getName());
    category.getStyle().set("display","block");
    LocalDateTime localdate = seminar.getDate();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    String formatDateTime = localdate.format(formatter);
    Label date = new Label(formatDateTime);
    Span link = new Span("Zum Veranstalter: "+seminar.getLink());
    link.getStyle().set("display","block");
    Span description = new Span("Beschreibung: " + seminar.getDescription());
    description.getStyle().set("display","block");
    details.add(title,date,category,locationAndPlz,link,description);
    details.setWidth("500px");
    details.setHeight("500px");
  }

  /*
  * To use in the presenter and sets the transferred Seminaries to the Grid
  *
  * Author: oppls7
  * */
  public void setSeminarList(List<Seminar> seminaries){
    seminarGrid.setItems(seminaries);
  }

}
