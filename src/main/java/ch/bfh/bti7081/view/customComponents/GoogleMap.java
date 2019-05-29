package ch.bfh.bti7081.view.customComponents;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;

/**
 * Google Map object as Vaadin Component.
 * The map is basing on a library which contains the configurable google map as HTML.
 * (See @HTMLImport and Polymer)
 * Properties are set via JavaScript.
 */
@Tag("google-map")
@HtmlImport("bower_components/google-map/google-map.html")
public class GoogleMap extends Component {

    public GoogleMap(String apiKey) {
        //Fits the zoom level so that all markers are present
        getElement().setProperty("fitToMarkers", true);
        getElement().getStyle().set("height", "100%");
        getElement().getStyle().set("width", "100%");
        getElement().setProperty("apiKey", apiKey);
    }

    public void setZoomLevel(int level){
        getElement().setProperty("zoom", level);
    }

    public void addMarker(GoogleMapMarker marker) {
        getElement().appendChild(marker.getElement());
    }

    public void setLatitude(double lat) {
        getElement().setProperty("latitude", lat);
    }

    public void setLongitude(double lon) {
        getElement().setProperty("longitude", lon);
    }

}

