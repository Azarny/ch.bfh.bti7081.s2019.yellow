package ch.bfh.bti7081.view;

import ch.bfh.bti7081.model.User;
import ch.bfh.bti7081.model.manager.UserManager;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

import java.util.Optional;
import java.util.stream.Collectors;

public class Layout extends VerticalLayout implements RouterLayout {

    private FormLayout formLayout = new FormLayout();
    private Dialog loginForm = new Dialog();
    private TextField userName = new TextField();
    private TextField userPw = new TextField();

    private void ShowLogin(){
        loginForm.open();
    }

    public Layout() {
        Button loginDialogBtn = new Button("Login");
        loginDialogBtn.addClickListener(Event -> ShowLogin());
        loginDialogBtn.getStyle().set("position","absolute").set("right","30px");
        HorizontalLayout menuBar = new HorizontalLayout(
                new RouterLink("Startseite", MainView.class),
                new RouterLink("Seminare", SeminarView.class),
                new RouterLink("FaQ", FaqView.class),
                new RouterLink("Forum", ForumView.class)
        );
        menuBar.add(loginDialogBtn);
        GenerateLoginLayout();
        this.add(menuBar, loginForm);
    }

    /*
     * Generates a Login Form with Vaadin-Binder
     *
     * Author: oppls7
     * */
    private void GenerateLoginLayout(){
        Binder<User> binder = new Binder<>();
        User userToLogin = new User();
        H2 title = new H2("Login");
        userName.setLabel("Benutzername: ");
        userPw.setLabel("Passwort: ");
        userName.setValueChangeMode(ValueChangeMode.EAGER);
        userPw.setValueChangeMode(ValueChangeMode.EAGER);
        binder.forField(userName)
                .withValidator(new StringLengthValidator(
                        "Bitte Benutzername eintragen", 1, null))
                .bind(User::getUsername, User::setUsername);
        binder.forField(userPw)
                .withValidator(new StringLengthValidator(
                        "Bitte Passwort eintragen", 1, null))
                .bind(User::getPassword, User::setPassword);

        Button loginBtn = new Button("Login");
        Label status = new Label();
        loginForm.add(status);
        //TODO Session Handling + besseres Feedback bei ButtonClick
        loginBtn.addClickListener(Event -> {
            status.setText("");
            if (binder.writeBeanIfValid(userToLogin)) {
                if(CheckLogin(userToLogin)) {
                    loginForm.removeAll();
                    loginForm.add(new H2("Willkommen " + userToLogin.getUsername()));
                }
                else {
                    status.setText("Falsches Passwort!");
                    return;
                }
                BinderValidationStatus<User> validate = binder.validate();
                String errorText = validate.getFieldValidationStatuses()
                        .stream().filter(BindingValidationStatus::isError)
                        .map(BindingValidationStatus::getMessage)
                        .map(Optional::get).distinct()
                        .collect(Collectors.joining(", "));
                status.setText("Login fehlgeschlagen: "+errorText);
                binder.readBean(null);
            }
        });
        formLayout.add(userName,userPw,loginBtn);
        loginForm.add(title,formLayout);
    }

    /*
     * Gets the user with his username and checks his password.
     *
     * Author: oppls7
     * */
    private boolean CheckLogin(User user) {
        User userCheck = UserManager.getUserByUsername(user.getUsername());
        return (userCheck.getPassword().equals(user.getPassword()));
    }
}
