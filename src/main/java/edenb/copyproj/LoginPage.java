package edenb.copyproj;

//import java.util.Date;
//import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("") @PageTitle("Login")
public class LoginPage extends VerticalLayout
{
   private String sessionId, userName;

   public LoginPage()
   {
      sessionId = VaadinSession.getCurrent().getSession().getId();
      userName = (String)VaadinSession.getCurrent().getSession().getAttribute("username");

      String welcomeMsg = "Welcome Guest!";
      if (userName != null)
         welcomeMsg = "Welcome " + userName.toUpperCase();

      add(new H1(welcomeMsg));
      add(new H3("( SessionID: " + sessionId + " )"));
      add(createLoginPanel()); // login field & buttons

      // set all components in the Center of page
      setSizeFull();
      setAlignItems(Alignment.CENTER);

   }

   // create a panel with some login field & buttons.
   private HorizontalLayout createLoginPanel()
   {
      HorizontalLayout panel = new HorizontalLayout();
      panel.setAlignItems(Alignment.BASELINE);

      if (userName == null)
      {
         TextField fieldName = new TextField("Your Name");
         Button btnLogin = new Button("Login", e -> {String enteredName = fieldName.getValue();login(enteredName);System.out.println(enteredName);
        });

         // when user type his name, check name validation. 
         fieldName.addValueChangeListener(e -> btnLogin.setEnabled(!fieldName.isInvalid()));

         panel.add(fieldName, btnLogin);
      }
      else
      {
         // This is a Loged User - let him Go back to Chat, or Logout.
         Button btnGotoChat = new Button("Back to canvas", e -> routeToChatPage());
         btnGotoChat.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

         Button btnLogout = new Button("Logout", VaadinIcon.SIGN_OUT.create(), e -> logout());
         btnLogout.setIconAfterText(true);
         btnLogout.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR); // RED button

         System.out.println("---------------> " + userName);

         panel.add(btnGotoChat, btnLogout);
      }

      return panel;
   }

   private void login(String value)
   {
      VaadinSession.getCurrent().getSession().setAttribute("username", value);
      routeToChatPage();
   }

   private void logout()
   {
      VaadinSession.getCurrent().getSession().invalidate();
      UI.getCurrent().getPage().reload();
   }

   private void routeToChatPage()
   {
      UI.getCurrent().getPage().setLocation("/canvas");
   }

}
