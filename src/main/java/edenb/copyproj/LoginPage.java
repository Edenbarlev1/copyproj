 package edenb.copyproj;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;

@PageTitle("LoginPage")
@Route(value = "/login", layout = AppMainLayout2.class)
public class LoginPage extends VerticalLayout {

    private UserService userService;

    public LoginPage(UserService userService) {
        
        this.userService = userService;
        final String USER_SESSION_KEY ="username";
        String loggedInUsername = (String) VaadinSession.getCurrent().getAttribute(USER_SESSION_KEY);

        if (loggedInUsername != null) {
           // System.out.println("-------- User NOT Authorized - can't use chat! --------" + loggedInUsername);
            UI.getCurrent().getPage().setLocation("/MainPage"); // Redirect to login page (HomePage).
            return; 
                }
        // Create login form
        LoginForm loginForm = new LoginForm();
        loginForm.setForgotPasswordButtonVisible(false); // Hide the "Forgot Password" link

        // Set login form properties
        loginForm.setForgotPasswordButtonVisible(false);

        // Register a listener for the LoginEvent
        loginForm.addLoginListener(e -> {
            String username = e.getUsername();
            String password = e.getPassword();

            // Now you can compare the username and password with the database
            User userExists = userService.getUserByID(username);
            VaadinSession.getCurrent().getSession().setAttribute("username",username);
            if (userExists != null && userExists.getPassword().equals(password)) {
                Notification.show("Login Successful!");
                UI.getCurrent().getSession().setAttribute("username", username);
                UI.getCurrent().navigate("/MainPage");
            } else if (userExists != null) {
                Notification.show("Invalid username or password. Please try again.");
                loginForm.setEnabled(true); // Disable login form after an unsuccessful attempt
            } else {
                Notification.show("Please register first.");
                loginForm.setEnabled(true); // Disable login form after an unsuccessful attempt

            }
        });

        

        // Create a button to navigate to the register page
        RouterLink registerLink = new RouterLink(" to Register", RegisterPage.class);

        // Set alignment and background
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setWidth("100%");
        setHeight("100vh");

        // Add components to the layout
        add(loginForm, registerLink);
    }
}

