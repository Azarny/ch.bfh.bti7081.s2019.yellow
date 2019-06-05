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
 */
@Tag("google-map")
@HtmlImport("bower_components/google-map/google-map.html")
public class GoogleMap extends Component {

    private boolean mapIsReady=false;

    /**
     * Author: walty1
     */
    public GoogleMap() {
        //Fits the zoom level so that all markers are present
        getElement().setProperty("fitToMarkers", true);
        getElement().getStyle().set("height", "100%");
        getElement().getStyle().set("width", "100%");
        this.addMapReadyListener(event->mapIsReady=true);
    }

    /**
     * Author: walty1
     * @param clickListener
     * @return
     */
    public Registration addMapReadyListener(ComponentEventListener<MapReadyEvent> clickListener) {
        //Event has to be activated in JS.
        getElement().setProperty("clickEvents", true);
        return super.addListener(MapReadyEvent.class, clickListener);
    }

    /**
     * Author: walty1
     * @param marker GoogleMapMarker to add
     */
    public void addMarker(GoogleMapMarker marker) {
        getElement().appendChild(marker.getElement());
    }

    /**
     * Author: walty1
     * Deletes all markers from the DOM.
     */
    public void resetMarkers(){
        if(getElement().getChildren().count()>0){
            getElement().removeAllChildren();
        }
    }

    public boolean isMapReady() {
        return mapIsReady;
    }

    public void setApiKey(String apiKey){
        getElement().setProperty("apiKey", apiKey);
    }
    public void setZoomLevel(int level){
        getElement().setProperty("zoom", level);
    }

    public void setLatitude(double lat) {
        getElement().setProperty("latitude", lat);
    }

    public void setLongitude(double lon) {
        getElement().setProperty("longitude", lon);
    }

}

