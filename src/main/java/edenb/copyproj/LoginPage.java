 package edenb.copyproj;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;


@Route("login")
@PageTitle("Login Page")
public class LoginPage extends VerticalLayout {

   private UserService userService;
   private DrawService drawService;

   public LoginPage(UserService userService, DrawService drawService) {
      this.userService = userService;
      this.drawService = drawService;   

        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
      
        Button loginButton = new Button("Login", event -> {
         String usernameValue = username.getValue();
         String passwordValue = password.getValue();
         if (userService.isUserExists(usernameValue, passwordValue)) {
             Notification.show("Login successful");
             VaadinSession.getCurrent().getSession().setAttribute("username",usernameValue);
             RouteTocanas();
         } else {
             Notification.show("User not found, redirecting to registration page");
             RouteToRegister();
         }
     });
        add(username, password, loginButton);
        setAlignItems(Alignment.CENTER);
    }
    private void RouteTocanas()
      {
            UI.getCurrent().getPage().setLocation("/MainPage");
      }
      private void RouteToRegister()
      {
        UI.getCurrent().getPage().setLocation("/register");
      }
      
}



