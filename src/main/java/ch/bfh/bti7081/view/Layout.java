package ch.bfh.bti7081.view;

import ch.bfh.bti7081.model.dto.UserDTO;
import ch.bfh.bti7081.presenter.UserPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.stream.Collectors;

public class Layout extends VerticalLayout implements RouterLayout {
    private FormLayout formLayout = new FormLayout();
    private Dialog loginForm = new Dialog();
    private TextField userName = new TextField();
    private PasswordField userPw = new PasswordField();
    private Button loginDialogBtn = new Button("Login");

    @Autowired
    private UserPresenter presenter;

    public Layout() {
        loginDialogBtn.addClickListener(Event -> showLogin());
        loginDialogBtn.getStyle().set("position", "absolute").set("right", "30px");
        HorizontalLayout menuBar = new HorizontalLayout(
                new RouterLink("Startseite", MainView.class),
                new RouterLink("Seminare", SeminarView.class),
                new RouterLink("FaQ", FaqView.class),
                new RouterLink("Forum", ForumView.class)
        );
        menuBar.add(loginDialogBtn);
        generateLoginLayout();
        this.add(menuBar, loginForm);
    }

    private void showLogin() {
        loginForm.open();
    }

    /*
     * Generates a Login Form with Vaadin-Binder
     *
     * Author: oppls7
     * */
    private void generateLoginLayout() {
        Binder<UserDTO> binder = new Binder<>();
        UserDTO userToLogin = new UserDTO();
        H2 title = new H2("Login");
        userName.setLabel("Benutzername: ");
        userPw.setLabel("Passwort: ");
        userName.setValueChangeMode(ValueChangeMode.EAGER);
        userPw.setValueChangeMode(ValueChangeMode.EAGER);
        binder.forField(userName)
                .withValidator(new StringLengthValidator(
                        "Bitte Benutzername eintragen", 1, null))
                .bind(UserDTO::getUsername, UserDTO::setUsername);
        binder.forField(userPw)
                .withValidator(new StringLengthValidator(
                        "Bitte Passwort eintragen", 1, null))
                .bind(UserDTO::getPassword, UserDTO::setPassword);

        Button loginBtn = new Button("Login");
        Label status = new Label();
        loginForm.add(status);
        //TODO Session Handling + besseres Feedback bei ButtonClick
        loginBtn.addClickListener(Event -> {
            status.setText("");
            if (binder.writeBeanIfValid(userToLogin)) {
                if (presenter.getUserByUsername(userToLogin.getUsername()) == null) {
                    status.setText("Benutzername unbekannt!");
                    return;
                } else if (checkLogin(userToLogin)) {
                    loginForm.removeAll();
                    loginForm.add(new H2("Willkommen " + userToLogin.getUsername()));
                    loginDialogBtn.getStyle().set("display", "none");
                } else {
                    status.setText("Falsches Passwort!");
                    return;
                }
                BinderValidationStatus<UserDTO> validate = binder.validate();
                String errorText = validate.getFieldValidationStatuses()
                        .stream().filter(BindingValidationStatus::isError)
                        .map(BindingValidationStatus::getMessage)
                        .map(Optional::get).distinct()
                        .collect(Collectors.joining(", "));
                status.setText("Login fehlgeschlagen: " + errorText);
                binder.readBean(null);
            }
        });
        formLayout.add(userName, userPw, loginBtn);
        loginForm.add(title, formLayout);
    }

    /*
     * Gets the user with his username and checks his password.
     *
     * Author: oppls7
     * */
    private boolean checkLogin(UserDTO user) {
        UserDTO userCheck = presenter.getUserByUsername(user.getUsername());
        if (userCheck != null) {
            return (userCheck.getPassword().equals(user.getPassword()));
        }
        return false;
    }
}