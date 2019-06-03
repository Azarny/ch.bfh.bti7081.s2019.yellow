package ch.bfh.bti7081.view.customComponents;

import ch.bfh.bti7081.model.dto.SeminarDTO;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Synchronize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.shared.Registration;

/**
 * Google Map object as Vaadin Component.
 * The marker is in HTML an is appended as a child to the map.
 * (See @HTMLImport and Polymer)
 * Properties are set via JavaScript.
 */
@Tag("google-map-marker")
@HtmlImport("bower_components/google-map/google-map-marker.html")
public class GoogleMapMarker extends Component {

    public GoogleMapMarker(double lat, double lon) {
        getElement().setProperty("latitude", lat);
        getElement().setProperty("longitude", lon);
    }

    public Registration addClickListener(ComponentEventListener<MarkerClickEvent> clickListener) {
        //Event has to be activated in JS.
        getElement().setProperty("clickEvents", true);
        return super.addListener(MarkerClickEvent.class, clickListener);
    }

    public void setDraggable(boolean draggable) {
        getElement().setProperty("draggable", draggable);
    }

    public void setTitle(String title){
        getElement().setProperty("title", title);
    }

    //Synchronises the location in case it was dragged by the user.
    @Synchronize("google-map-marker-dragend")
    public double getLatitude() {
        String property = getElement().getProperty("latitude");
        return Double.valueOf(property);
    }
    //Synchronises the location in case it was dragged by the user.
    @Synchronize("google-map-marker-dragend")
    public double getLongitude() {
        String property = getElement().getProperty("longitude");
        return Double.valueOf(property);
    }
}