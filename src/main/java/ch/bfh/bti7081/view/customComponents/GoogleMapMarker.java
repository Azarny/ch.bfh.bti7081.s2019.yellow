package ch.bfh.bti7081.view.customComponents;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
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

    /**
     * Creates a new Marker for the map.
     *
     * @param lat latitude of the location
     * @param lng longitude of the location
     * @author walty1
     */
    public GoogleMapMarker(double lat, double lng) {
        getElement().setProperty("latitude", lat);
        getElement().setProperty("longitude", lng);
    }

    /**
     * Adds a listener that checks if the marker has been clicked on.
     *
     * @param clickListener (Own class for listener)
     * @return Registration for listener.
     * @author walty1
     */
    public Registration addClickListener(ComponentEventListener<MarkerClickEvent> clickListener) {
        //Event has to be activated in JS.
        getElement().setProperty("clickEvents", true);
        return super.addListener(MarkerClickEvent.class, clickListener);
    }

    /**
     * By standard, a marker is draggable, means, its location can be changed on the map by the user.
     *
     * @param draggable boolean
     * @author walty1
     */
    public void setDraggable(boolean draggable) {
        getElement().setProperty("draggable", draggable);
    }

    public void setTitle(String title) {
        getElement().setProperty("title", title);
    }
}