package edenb.copyproj;
 
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;

public class AppMainLayout2 extends AppLayout{
    private String userName;
    private GroupService groupService;
    private UserService userService;

   public AppMainLayout2(GroupService groupService,UserService userService)
   {
      this.groupService = groupService;
      this.userService = userService;
      createHeader();
   }
   private void createHeader() {
      H3 logo = new H3("CanvasApp");
      logo.getStyle().setColor("blue");
  
      HorizontalLayout header = new HorizontalLayout(); // Declare header here

          Span messageSpan = new Span("Please login.");
    
          header.add(logo,messageSpan);
          header.setWidthFull();
          header.setAlignItems(Alignment.BASELINE);
          header.setPadding(true);
         // header.expand(space);
          addToNavbar(header);
      }
  
     
    }