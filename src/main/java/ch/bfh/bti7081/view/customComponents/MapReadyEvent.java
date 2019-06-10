package ch.bfh.bti7081.view.customComponents;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;

/**
 * Event that declares if a google-map-object has loaded on the client.
 * Has to be added to a map-object.
 *
 * @author walty1
 */
@DomEvent("google-map-ready")
public class MapReadyEvent extends ComponentEvent<GoogleMap> {
    public MapReadyEvent(GoogleMap source, boolean fromClient) {
        super(source, fromClient);
    }
}
