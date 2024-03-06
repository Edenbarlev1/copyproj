package edenb.copyproj;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(" ")
@PageTitle("Home Page")
public class HomePage extends VerticalLayout {

    public HomePage() {
        // יצירת כפתור התחברות
        Button loginButton = new Button("Login");
        H2 h2 = new H2("welcome to home page");
        loginButton.addClickListener(event -> {
            RouteToLogin();
        });

        // יצירת כפתור הרשמה
        Button registerButton = new Button("Register");
        registerButton.addClickListener(event -> {     
            RouteToRegister();      
        });

        HorizontalLayout buttonsLayout = new HorizontalLayout(h2,loginButton, registerButton);
        add(h2, buttonsLayout);
        setHorizontalComponentAlignment(Alignment.CENTER, buttonsLayout,h2);
    
    }
      private void RouteToLogin()
      {
        UI.getCurrent().getPage().setLocation("/login");
      }

      private void RouteToRegister()
      {
        UI.getCurrent().getPage().setLocation("/register");
      }

}

