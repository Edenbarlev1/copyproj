package edenb.copyproj;

import java.util.Date;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = " ",layout = AppMainLayout.class)
@PageTitle("Home Page")
public class HomePage extends VerticalLayout {

  private static final String IMAGE_LOGO_URL = "https://imgproxy.icebreakerspot.com/XrTYBXDszQ0lni_xQanoL5f2oaNgnfkP5pgd5lx0K6s/w:768/q:75/czM6Ly9pY2VicmVh/a2Vyc3BvdC1hc3Nl/dHMvOWVzd29ucWJt/eHRoeG16M2k1NWsx/MG0xMW1rdA.webp";
  
    public HomePage() {

        // יצירת כפתור התחברות
      //   Button loginButton = new Button("Login");
      //   loginButton.getStyle().set("color", "black");
      //  // H2 h2 = new H2("Home Page");
      //   //H1 h1 = new H1("Welcome Guest!");
      //   loginButton.addClickListener(event -> {
      //       RouteToLogin();
      //   });
        
        H2 h2 = new H2("Home Page");
        H1 h1 = new H1("Welcome Guest!");

        // יצירת כפתור הרשמה
        // Button registerButton = new Button("Register");
        // registerButton.getStyle().set("color", "black");
        // registerButton.addClickListener(event -> {     
        //     RouteToRegister();      
        // });
        Image img = new Image(IMAGE_LOGO_URL, "photo");
        HorizontalLayout buttonsLayout = new HorizontalLayout(h2);//,loginButton, registerButton);
       img.setHeight("280px");
       Text txt = new Text(new Date()+"");
        add(h2,txt,img,h1,buttonsLayout);
        setHorizontalComponentAlignment(Alignment.CENTER, buttonsLayout,h2);

        setAlignItems(Alignment.CENTER);
        this.getStyle().set("background-color", "#FFF8E7");
          setSizeFull();
    }
      private void RouteToLogin()
      {
        UI.getCurrent().getPage().setLocation("/login");
      }

      private void RouteToRegister()
      {
        UI.getCurrent().getPage().setLocation("/register");
      }

      public HorizontalLayout CreateTitle()
      {
        HorizontalLayout l = new HorizontalLayout();
        
        return l;
      }

}

