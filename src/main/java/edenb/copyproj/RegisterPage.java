package edenb.copyproj;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("register")
@PageTitle("Register")
public class RegisterPage extends VerticalLayout {

    private UserService userService;

    public RegisterPage(UserService userService) {
        this.userService = userService;

        TextField usernameField = new TextField("שם משתמש");
        PasswordField passwordField = new PasswordField("סיסמה");
        Button signupButton = new Button("הרשם", event -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            
            // בדיקה אם המשתמש כבר קיים במערכת
            if (userService.isUserExists(username,password)) {
                Notification.show("שם המשתמש כבר קיים במערכת");
            } else {
                // הוספת המשתמש למאגר הנתונים
                userService.addUser(new User(username, password));
                Notification.show("המשתמש נרשם בהצלחה!");
            }
            RouteToLogin();
        });

        add(usernameField, passwordField, signupButton);
        setAlignItems(Alignment.CENTER);
    }
    private void RouteToLogin()
      {
        UI.getCurrent().getPage().setLocation("/login");
      }
}
