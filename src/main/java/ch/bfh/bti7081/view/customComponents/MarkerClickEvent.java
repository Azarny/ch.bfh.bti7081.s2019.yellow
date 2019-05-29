package ch.bfh.bti7081.view.customComponents;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;

@DomEvent("google-map-marker-click")
public class MarkerClickEvent extends ComponentEvent<GoogleMapMarker> {
    public MarkerClickEvent(GoogleMapMarker source, boolean fromClient) {
        super(source, fromClient);
    }
}
