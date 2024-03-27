package edenb.copyproj;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("RegisterPage")
@Route(value = "/register",layout = AppMainLayout2.class)
public class RegisterPage extends VerticalLayout {

    private UserService userService;

    public RegisterPage(UserService userService) {
        this.userService = userService;
        

        // Set up the main layout
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
       // getStyle().set("background-color", "#7FDBFF"); // Example: bright sky blue

        // Title
        H1 title = new H1("Register");

        // Form layout for centered content
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setWidth("auto");
        formLayout.setAlignItems(Alignment.CENTER);
        formLayout.setPadding(false);

        // Input fields
        TextField usernameField = new TextField("Username");
        usernameField.setWidth("300px");
        usernameField.addClassName(LumoUtility.Margin.Bottom.LARGE);

        PasswordField passwordField = new PasswordField("Password");
        passwordField.setWidth("300px");
        passwordField.addClassName(LumoUtility.Margin.Bottom.LARGE);

        // Register button
        Button registerButton = new Button("Register", event -> handleRegister(usernameField, passwordField));
        registerButton.addThemeNames("primary");
        registerButton.setWidth("300px");

        // Adding components to the form layout
        formLayout.add(title, usernameField, passwordField, registerButton);

        // Adding formLayout to the main layout
        add(formLayout);
    }

    private void handleRegister(TextField usernameField, PasswordField passwordField) {
        String username = usernameField.getValue();
        String password = passwordField.getValue();

        if (username.isEmpty()) {
            Notification.show("Username cannot be empty");
            return;
        }

        if (password.isEmpty()) {
            Notification.show("Password cannot be empty");
            return;
        }

        if (userService.isUserExists(username)) {
            Notification.show("Username already exists");
            return;
        }

        User user = new User(username, password);
        userService.addUser(user);
        Notification.show("User registered successfully");
        UI.getCurrent().getSession().setAttribute("user", username);
        UI.getCurrent().navigate("/login");
    }
}