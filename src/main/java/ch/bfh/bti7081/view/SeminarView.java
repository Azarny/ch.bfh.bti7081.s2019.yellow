package ch.bfh.bti7081.view;

import ch.bfh.bti7081.model.manager.SeminarCategoryManager;
import ch.bfh.bti7081.model.manager.SeminarManager;
import ch.bfh.bti7081.model.seminar.Seminar;
import ch.bfh.bti7081.model.seminar.SeminarCategory;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.Route;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Route(value = "seminar", layout = Layout.class)
public class SeminarView extends VerticalLayout {
    private VerticalLayout SeminarFilterLayout = new VerticalLayout();
    private VerticalLayout SeminarListLayout = new VerticalLayout();
    private Label lbl = new Label("");
    private Dialog details = new Dialog();

    public SeminarView(List<Seminar> seminarList){
        GenerateFilterLayout();
        GenerateListLayout(seminarList);
        VerticalLayout leftLayout = new VerticalLayout();
        HorizontalLayout contentLayout = new HorizontalLayout();
        VerticalLayout rightLayout = new VerticalLayout();
        contentLayout.add(leftLayout, rightLayout);
        leftLayout.add(SeminarFilterLayout,SeminarListLayout, details);
        H1 title = new H1("Seminarfinder");
        this.add(title, contentLayout);
    }

    private void GenerateListLayout(List<Seminar> seminarList){
        Grid<Seminar> seminarGrid = new Grid<>();
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd.MM.yyyy");
        seminarGrid.setItems(seminarList);
        seminarGrid.addColumn(TemplateRenderer.<Seminar> of(
                "<div style='padding:10px'><div style='font-weight:bold'>Seminar: [[item.title]]<br></div>"+
                "<div>Datum: [[item.date]] <div style='float:right'> Ort: [[item.location]]</div></div></div>")
                .withProperty("title", Seminar::getTitle)
                .withProperty("date",
                        seminar -> formatter.format(seminar.getDate()))
                .withProperty("location", Seminar::getLocation))
                .setFlexGrow(6);
        seminarGrid.setSelectionMode(Grid.SelectionMode.NONE);
        seminarGrid.addItemClickListener(
                event -> ShowDetails(event.getItem()));
        SeminarListLayout.add(seminarGrid);
    }

    private void ShowDetails(Seminar seminar){
        details.removeAll();
        GenerateDialog(seminar);
        details.open();
    }

    private void GenerateDialog(Seminar seminar){
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

    private void GenerateFilterLayout(){
        FormLayout filterLayout = new FormLayout();
        TextField searchTf = new TextField();
        searchTf.setLabel("Suche:");
        searchTf.setPlaceholder("Suchen...");
        DatePicker fromDateDp = new DatePicker();
        fromDateDp.setLabel("Datum von:");
        fromDateDp.setPlaceholder("von...");
        DatePicker toDateDp = new DatePicker();
        toDateDp.setLabel("Datum bis:");
        toDateDp.setPlaceholder("bis...");
        ComboBox<SeminarCategory> categoriesCb = new ComboBox<>();
        categoriesCb.setLabel("Kategorien:");
        categoriesCb.setPlaceholder("Kategorie wählen...");
        categoriesCb.setItemLabelGenerator(SeminarCategory::getName);
        categoriesCb.setItems(SeminarCategoryManager.getSeminarCategories());
        categoriesCb.addValueChangeListener(event -> {
            SeminarCategory selectedCategory = categoriesCb.getValue();
            if (selectedCategory != null) {
                lbl.setText("Selected Category: " + selectedCategory.getName());
            } else {
                lbl.setText("No Category is selected");
            }
        });
        TextField ortTf = new TextField();
        ortTf.setLabel("Ort:");
        ortTf.setPlaceholder("Ort...");
        Button filterBtn = new Button("Filter anwenden");
        filterLayout.add(searchTf, fromDateDp, toDateDp, categoriesCb, ortTf,filterBtn);

        filterLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("21em", 2));
        SeminarFilterLayout.add(filterLayout);
    }

}
