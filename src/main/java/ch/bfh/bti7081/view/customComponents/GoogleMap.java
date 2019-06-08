package ch.bfh.bti7081.view.customComponents;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.shared.Registration;

/**
 * Google Map object as Vaadin Component.
 * The map is basing on a library which contains the configurable google map as HTML.
 * (See @HTMLImport and Polymer)
 * Properties are set via JavaScript.
 * Please use the isMapReady boolean to check if markers can be set.
 *
 * @author walty1
 */
@Tag("google-map")
@HtmlImport("bower_components/google-map/google-map.html")
public class GoogleMap extends Component {

    private boolean mapIsReady = false;

    /**
     * Constructs a basic Google-Maps object to add to a Vaadin View.
     * Consider using "isMapReady" before adding any objects.
     *
     * @author walty1
     */
    public GoogleMap() {
        //Fits the zoom level so that all markers are present
        getElement().setProperty("fitToMarkers", true);
        getElement().getStyle().set("height", "100%");
        getElement().getStyle().set("width", "100%");
        //By using this listener, isMapReady will deliver true information.
        this.addMapReadyListener(event -> mapIsReady = true);
    }

    /**
     * Add a listener to the map to check if elements can be added already.
     *
     * @param mapReadyListener The Listener (own class)
     * @return Registrates a listener.
     * @author walty1
     */
    public Registration addMapReadyListener(ComponentEventListener<MapReadyEvent> mapReadyListener) {
        return super.addListener(MapReadyEvent.class, mapReadyListener);
    }

    /**
     * Adds a Google-Maps-Marker to the current map object.
     *
     * @param marker GoogleMapMarker to add
     * @author walty1
     */
    public void addMarker(GoogleMapMarker marker) {
        getElement().appendChild(marker.getElement());
    }

    /**
     * Deletes all markers from the DOM.
     *
     * @author walty1
     */
    public void resetMarkers() {
        if (getElement().getChildren().count() > 0) {
            getElement().removeAllChildren();
        }
    }

    /**
     * Returns the state of the map and if adding elements is already allowed.
     *
     * @return state
     * @author walty1
     */
    public boolean isMapReady() {
        return mapIsReady;
    }

    /**
     * An API-key for Google Maps provided by Google is needed.
     *
     * @param apiKey Google-Maps-Api-Key
     * @author walty1
     */
    public void setApiKey(String apiKey) {
        getElement().setProperty("apiKey", apiKey);
    }

    public void setZoomLevel(int level) {
        getElement().setProperty("zoom", level);
    }

    public void setLatitude(double lat) {
        getElement().setProperty("latitude", lat);
    }

    public void setLongitude(double lon) {
        getElement().setProperty("longitude", lon);
    }
}

