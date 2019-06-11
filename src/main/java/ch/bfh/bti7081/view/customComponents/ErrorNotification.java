package ch.bfh.bti7081.view.customComponents;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;

/**
 * This is a custom object to display errors to the user.
 * The constants can be used to create a consistent experience.
 * Adds a css-class to the content, can be styled using CSS.
 *
 * @author walty1
 */
public class ErrorNotification extends Notification {
    private final static String CLASSNAME = "error-notification";
    private final static int DURATION_IN_MS = 5000;

    /**
     * Creates the notification.
     * Needs to be added to a view, disappears automatically.
     *
     * @param message Message to display.
     * @author walty1
     */
    public ErrorNotification(String message) {
        //DIV is used for css-styling of text
        //Advice found under: https://vaadin.com/components/vaadin-notification/java-examples
        Div content = new Div();
        content.addClassName(CLASSNAME);
        content.setText(message);
        add(content);
        setDuration(DURATION_IN_MS);
        setPosition(Notification.Position.TOP_END);
        open();
    }
}
