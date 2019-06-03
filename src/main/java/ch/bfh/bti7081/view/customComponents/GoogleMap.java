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

    public GoogleMap() {
        //Fits the zoom level so that all markers are present
        getElement().setProperty("fitToMarkers", false);
        getElement().getStyle().set("height", "100%");
        getElement().getStyle().set("width", "100%");
    }

    public void setApiKey(String apiKey){
        getElement().setProperty("apiKey", apiKey);
    }
    public void setZoomLevel(int level){
        getElement().setProperty("zoom", level);
    }

    public void addMarker(GoogleMapMarker marker) {
        getElement().appendChild(marker.getElement());
    }

    public void resetMarkers(){
        if(getElement().getChildren().count()>0){
            getElement().removeAllChildren();
        }
    }

    public void setLatitude(double lat) {
        getElement().setProperty("latitude", lat);
    }

    public void setLongitude(double lon) {
        getElement().setProperty("longitude", lon);
    }

}

