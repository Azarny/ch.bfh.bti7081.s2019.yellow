package ch.bfh.bti7081.view;

import ch.bfh.bti7081.presenter.UserPresenter;
import ch.bfh.bti7081.presenter.dto.UserDTO;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
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
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This View contains components that are present on every page of our product.
 * Navigation-Header and login.
 *
 * @author heuzl1
 * @author oppls7
 */
@StyleSheet("styles/style.css")
public class Layout extends VerticalLayout implements RouterLayout {
    @Autowired
    private UserPresenter presenter;
    private HorizontalLayout menuBar = new HorizontalLayout();
    private Label filler = new Label("");
    // elements for login
    private Button loginDialogBtn = new Button("Login");
    private HorizontalLayout loginLayout = new HorizontalLayout(loginDialogBtn);
    // elements for logged in user
    private Label loggedInUser = new Label();
    private Button logoutBtn = new Button("Logout");
    private HorizontalLayout loggedInLayout = new HorizontalLayout(loggedInUser, logoutBtn);
    // LoginForm
    private FormLayout formLayout = new FormLayout();
    private Dialog loginForm = new Dialog();
    private TextField userName = new TextField();
    private PasswordField userPw = new PasswordField();

    /**
     * Generates the view of routerbar and login.
     *
     * @author heuzl1
     */
    public Layout() {
        HorizontalLayout links = new HorizontalLayout(
                new RouterLink("Startseite", MainView.class),
                new RouterLink("Seminare", SeminarView.class),
                new RouterLink("FaQ", FaqView.class),
                new RouterLink("Forum", ForumView.class)
        );
        menuBar.add(links);

        // check if user is logged in

        String userName = (String) VaadinSession.getCurrent().getAttribute("userName");
        if ((userName == null) || ("".equals(userName))) {
            // user not logged in
            loginDialogBtn.addClickListener(Event -> showLogin());
            loginLayout.getClassNames().add("login");
            menuBar.add(loginLayout);
        } else {
            loggedInUser.setText(userName);
            logoutBtn.addClickListener(Event -> logout());
            loggedInLayout.getClassNames().add("login");
            menuBar.add(loggedInLayout);
        }

        menuBar.getClassNames().add("mainnav");
        generateLoginLayout();
        this.add(menuBar, loginForm);
    }

    private void showLogin() {
        loginForm.open();
    }

    /**
     * @author heuzl1
     */
    private void logout() {
        VaadinSession.getCurrent().setAttribute("userName", null);
        refreshSite();
    }

    /**
     * @author heuzl1
     */
    private void refreshSite() {
        UI.getCurrent().getPage().reload();
    }

    /**
     * Generates a Login Form with Vaadin-Binder
     *
     * @author oppls7
     */
    private void generateLoginLayout() {
        Binder<UserDTO> binder = new Binder<>();
        UserDTO userToLogin = new UserDTO();
        H2 title = new H2("Login");
        userName.setLabel("Benutzername: ");
        userPw.setLabel("Passwort: ");
        userName.setValueChangeMode(ValueChangeMode.EAGER);
        userPw.setValueChangeMode(ValueChangeMode.EAGER);
        Label status = new Label();
        binder.forField(userName)
                .withValidator(new StringLengthValidator(
                        "Bitte Benutzername eintragen", 1, null))
                .bind(UserDTO::getUsername, UserDTO::setUsername);
        binder.forField(userPw)
                .withValidator(new StringLengthValidator(
                        "Bitte Passwort eintragen", 1, null))
                .bind(UserDTO::getPassword, (userDTO, password) -> {
                    try {
                        userDTO.setEncryptedPassword(presenter.encryptPassword(userName.getValue(), password));
                    } catch (Exception e) {
                        status.setText("Could not save Password.");
                    }
                });

        Button loginBtn = new Button("Login");

        loginForm.add(status);
        //TODO besseres Feedback bei ButtonClick
        loginBtn.addClickListener(Event -> {
            status.setText("");
            if (binder.writeBeanIfValid(userToLogin)) {
                if (presenter.getUserByUsername(userToLogin.getUsername()) == null) {
                    status.setText("Benutzername unbekannt!");
                    return;
                } else if (checkLogin(userToLogin)) {
                    loginForm.removeAll();
                    VaadinSession.getCurrent().setAttribute("userName", userToLogin.getUsername());
                    loginForm.add(new H2("Willkommen " + userToLogin.getUsername()));
                    loginDialogBtn.getStyle().set("display", "none");
                    refreshSite();
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

    /**
     * Gets the user with his username and checks his password.
     *
     * @author oppls7
     */
    private boolean checkLogin(UserDTO user) {
        UserDTO userCheck = presenter.getUserByUsername(user.getUsername());
        if (userCheck != null) {
            return (userCheck.getPassword().equals(user.getPassword()));
        }
        return false;
    }
}