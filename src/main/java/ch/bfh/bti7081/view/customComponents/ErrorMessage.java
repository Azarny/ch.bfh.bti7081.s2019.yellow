package ch.bfh.bti7081.view.customComponents;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;

public class ErrorMessage extends Notification {
    private final String CLASSNAME = "error-notification";
    private final int DURATION_IN_MS = 5000;

    /**
     * This is a custom object to display errors to the user.
     * The constants can be used to create a consistent experience.
     * @param message
     */
    public ErrorMessage(String message) {
        //DIV is used for css-styling of text
        Div content = new Div();
        content.addClassName(CLASSNAME);
        content.setText(message);

        add(content);
        setDuration(DURATION_IN_MS);
        setPosition(Notification.Position.TOP_END);

        open();
    }
}
