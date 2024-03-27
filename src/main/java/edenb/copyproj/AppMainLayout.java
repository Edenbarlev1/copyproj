package edenb.copyproj;
 
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.server.VaadinSession;

public class AppMainLayout extends AppLayout{
    private String userName;
    private GroupService groupService;
    private UserService userService;

   public AppMainLayout(GroupService groupService,UserService userService)
   {
      this.groupService = groupService;
      this.userService = userService;
      createHeader();
   }
   private void createHeader() {
      H3 logo = new H3("CanvasApp");
      logo.getStyle().setColor("blue");
  
      Span space = new Span("");
      String sessionID = VaadinSession.getCurrent().getSession().getId();
      userName = (String) VaadinSession.getCurrent().getSession().getAttribute("username");
  
      HorizontalLayout header = new HorizontalLayout(); // Declare header here
  
      if (userName != null) {
          Span userSpan = new Span("Hello: " + userName);
          Span sessionSpan = new Span("session: " + sessionID);

          Button btnLogout = new Button("Logout", e -> {
            groupService.removeUserFromActiveGroup(userName);
              VaadinSession.getCurrent().getSession().invalidate();
              UI.getCurrent().getPage().setLocation("/login");
          });

           Button btnMain = new Button("Main", e -> {
              UI.getCurrent().getPage().setLocation("/MainPage");
          });

          Button btnHistory = new Button("My Draws", e -> {
            UI.getCurrent().getPage().setLocation("/history");
        });

        Button btnHome = new Button("Home", e -> {
         UI.getCurrent().getPage().setLocation("/");
     });
  
          btnLogout.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
          btnLogout.getStyle().setColor("black");
          btnLogout.getStyle().setBackgroundColor("red");
  
          btnHome.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
          btnMain.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
          btnHistory.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);

          
          header.add(logo, userSpan, sessionSpan, space,btnMain,btnHistory,btnHome, btnLogout);
      } else {
          Span messageSpan = new Span("Please login.");
          Button btnLogin = new Button("Login", e -> {
              VaadinSession.getCurrent().getSession().invalidate();
              UI.getCurrent().getPage().setLocation("/login");
          });
          Button btnRegister = new Button("Register", e -> {
              VaadinSession.getCurrent().getSession().invalidate();
              UI.getCurrent().getPage().setLocation("/register");
          });
          btnLogin.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
          btnRegister.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
          header.add(logo,messageSpan,space, btnRegister, btnLogin);
      }
  
      header.setWidthFull();
      header.setAlignItems(Alignment.BASELINE);
      header.setPadding(true);
      header.expand(space);
      addToNavbar(header);
  }
}

// package edenb.copyproj;


// import com.vaadin.flow.component.UI;
// import com.vaadin.flow.component.applayout.AppLayout;
// import com.vaadin.flow.component.button.Button;
// import com.vaadin.flow.component.button.ButtonVariant;
// import com.vaadin.flow.component.html.H3;
// import com.vaadin.flow.component.html.Span;
// import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
// import com.vaadin.flow.component.orderedlayout.FlexComponent;
// import com.vaadin.flow.router.Location;
// import com.vaadin.flow.server.VaadinSession;

// import java.util.ArrayList;
// import java.util.List;

// public class AppMainLayout extends AppLayout {
//     private static final String LOGIN_VIEW = "/login";
//     private static final String REGISTER_VIEW = "/register";
//    private static final String HISTORY_VIEW = "/history";
//    private static final String MAIN_VIEW = "/MainPage";
//    // private static final String ADMIN_PAGE_VIEW = "/adminPage";
//    // private static final String SOLUTIONS_PAGE_VIEW = "/show";
//     private static final String HOME_PAGE_VIEW = "/";

//     private UserService userService;
//     private Button selectedButton;
//     private List<Button> buttons = new ArrayList<>();

//     public AppMainLayout(UserService userService) {
//         this.userService = userService;
//         createHeader();
//     }

//     private void createHeader() {
//         H3 logo = new H3("CanvasApp");
//         logo.getStyle().setColor("blue");

//         String userName = (String) VaadinSession.getCurrent().getAttribute("username");
//         Span userSpan = new Span("👤 Welcome to Our Website: " + (userName != null ? userName : "Guest"));

//         HorizontalLayout header = new HorizontalLayout();
//         header.add(logo, userSpan);
//         header.setFlexGrow(1, logo); // Ensuring the logo takes as much space as it needs

//         HorizontalLayout buttonLayout = new HorizontalLayout();
//         buttonLayout.setSpacing(true); // Adding some space between buttons

//         if (userName != null) {

//            Button btnHistory = createButton("My Draws", HISTORY_VIEW);
//            Button btnMain = createButton("Main Page", MAIN_VIEW);
//           //  Button btnAllAnswers = createButton("Solutions Page", SOLUTIONS_PAGE_VIEW);
//             Button btnHomePage = createButton("Home Page", HOME_PAGE_VIEW);

//             buttons.add(btnHistory);
//             buttons.add(btnMain);
//           //  buttons.add(btnAllAnswers);
//             buttons.add(btnHomePage);

//             User user = userService.getUserByID(userName);
            

//             Button btnLogout = new Button("Logout", e -> {
//                 VaadinSession.getCurrent().getSession().invalidate();
//                 UI.getCurrent().navigate(LOGIN_VIEW);
//             });

//             Location location = UI.getCurrent().getActiveViewLocation();

//             btnLogout.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
//             btnLogout.getStyle().setColor("black");
//             btnLogout.getStyle().setBackgroundColor("red");

//             buttonLayout.add(btnHomePage, btnLogout);
//             buttonLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
//             String path = "/" + location.getPath();

//         } else {
//             Button btnLogin = createButton("Login", LOGIN_VIEW);
//             Button btnRegister = createButton("Register", REGISTER_VIEW);
//             buttonLayout.add(btnLogin, btnRegister);
//         }

//         buttonLayout.setPadding(true);
//         header.add(buttonLayout);
//         header.setWidthFull();
//         header.setAlignItems(FlexComponent.Alignment.CENTER); // Align elements vertically centered
//         header.setJustifyContentMode(FlexComponent.JustifyContentMode.END); // Align buttons to the right

//         addToNavbar(header);
//         selectButtonBasedOnPath();
//     }

//     private Button createButton(String caption, String route) {
//         Button button = new Button(caption);
//         button.addClickListener(e -> {
//             UI.getCurrent().navigate(route);
//             selectButton(button);
//         });
//         button.setId(route);
//         System.out.println(button.getId());
//         return button;
//     }

//     private void selectButton(Button button) {
//         if (selectedButton != null) {
//             selectedButton.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
//         }
//         button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//         selectedButton = button;
//     }

//     private void selectButtonBasedOnPath() {
//         Location location = UI.getCurrent().getActiveViewLocation();
//         String path = "/" + location.getPath();
//         System.out.println("CURRENT path is:"+path);
//         for (Button button : buttons) {
//             String buttonPath = button.getId().orElse("");
//             System.out.println("path is:"+buttonPath);

//             if (buttonPath.equals(path)) {
//                 System.out.println("the chosse path is:"+buttonPath);
//                 selectButton(button);
//                 return;
//             }
//         }
//     }
    
// }



