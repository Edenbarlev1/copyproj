package edenb.copyproj;

import java.util.List;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import edenb.copyproj.GroupService.OnlineUserChangeListener;

import com.vaadin.flow.router.PageTitle;

@Route(value = "/MainPage", layout = AppMainLayout.class)
@PageTitle("Main")

public class MainPage extends VerticalLayout {
   // private boolean iscreator=false;
    private DrawService drawService;
    private GroupService groupService;
    private String userName;
    private String groupName;
    private boolean isCreator = false;
    public MainPage(DrawService drawService, GroupService groupService) {

        this.drawService = drawService;
        this.groupService = groupService;
      
        this.getStyle().set("background-color", "#FFF8E7");
        setSizeFull();

        if (!login()) {
            UI.getCurrent().getPage().setLocation("/login"); 
            return;
        }
        
        H2 h2 = new H2("Main Page");
        Button btn2 = new Button("my draws", event -> {
            UI.getCurrent().getPage().setLocation("/history");
        });

    ComboBox<ActiveGroup> comboBox = new ComboBox<>("please choose group to join");
    comboBox.setItems(groupService.getAllGroups());
    comboBox.setItemLabelGenerator(ActiveGroup::getNameGroup);
    //comboBox.setHelperText("please choose group");
    Button joiButton = new Button("Join group", event -> {
        ActiveGroup selectedGroup = comboBox.getValue();
        if (selectedGroup != null) {
            String username = (String) VaadinSession.getCurrent().getSession().getAttribute("username");
            VaadinSession.getCurrent().getSession().setAttribute("groupname", selectedGroup.getNameGroup());
            String sessionID = VaadinSession.getCurrent().getSession().getId();
            groupService.addUserAndSessionToGroup(username, sessionID, selectedGroup.getNameGroup());
            UI.getCurrent().getPage().setLocation("/canvas");
        } else {
            Notification.show("Please select a group to join");
        }
    });

        TextField groupName = new TextField("create name of group");
        Button codeButton = new Button("Create group", event -> {
   
           handleRegister(groupName);

           groupService.updateOnlineliseteners();
           
           
        });

        add(h2,btn2,comboBox, joiButton,groupName, codeButton);
        setAlignItems(Alignment.CENTER);

           refreshComboBox();
           
            groupService.addOnlineChangeListener(new OnlineUserChangeListener() {
                @Override
                public void onChange2() {
                    UI ui = getUI().orElse(null);
                    if (ui != null) {
                        ui.access(() -> refreshComboBox());
                    }
                }
            });
    }

    private boolean login() {
        userName = (String) VaadinSession.getCurrent().getSession().getAttribute("username");
        return userName != null;
    }  

    private void handleRegister(TextField groupName) {
        String gn = groupName.getValue();
       

        if (gn.isEmpty()) {
            Notification.show("groupname cannot be empty");
            return;
        }

        if (groupService.isGroupNameExists(gn)==true) {
            Notification.show("Groupname already exists");
            return;
        }

        String username = (String) VaadinSession.getCurrent().getSession().getAttribute("username");
        VaadinSession.getCurrent().getSession().setAttribute("groupname",groupName.getValue());
        String sessionID = VaadinSession.getCurrent().getSession().getId(); 
        groupService.addUserAndSessionToNewGroup(username, sessionID,groupName.getValue());
        drawService.addUserToDraw(username);
        VaadinSession.getCurrent().setAttribute("isCreator", true); 
        isCreator = true;
         UI.getCurrent().getPage().setLocation("/canvas");
    }
    
    private void refreshComboBox(){
        List<ActiveGroup> getAllGroups = groupService.getAllGroups();
    }
    

    
}


