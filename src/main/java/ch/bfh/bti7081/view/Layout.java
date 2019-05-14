package ch.bfh.bti7081.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

public class Layout extends VerticalLayout implements RouterLayout {
    private Dialog loginMask = new Dialog();

    private void showLogin(){
        loginMask.open();
    }

    public Layout() {
        FormLayout formLayout = new FormLayout();
        Button loginBtn = new Button("Login");
        loginBtn.addClickListener(Event -> showLogin());
        loginBtn.getStyle().set("position","absolute").set("right","30px");
        HorizontalLayout menuBar = new HorizontalLayout(
                new RouterLink("Startseite", MainView.class),
                new RouterLink("Seminare", SeminarView.class),
                new RouterLink("FaQ", FaqView.class),
                new RouterLink("Forum", ForumView.class)
        );
        menuBar.add(loginBtn);
        H2 title = new H2("Login");
        TextField userName = new TextField();
        userName.setLabel("Benutzername: ");
        TextField userPw = new TextField();
        userPw.setLabel("Passwort: ");
        formLayout.add(userName,userPw);
        loginMask.add(title,formLayout);
        this.add(menuBar, loginMask);
    }
}
