package edenb.copyproj;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

@Route("/MainPage")
@PageTitle("Main")
public class MainPage extends VerticalLayout {
    
    public MainPage() {
        Button btn1 = new Button("Canvas", event -> {
            UI.getCurrent().getPage().setLocation("/canvas");
        });
        Button btn2 = new Button("History", event -> {
            UI.getCurrent().getPage().setLocation("/history");
        });

        TextField codeteam = new TextField("join Code team");
        Button codeButton = new Button("Create team", event -> {
         codeteam.getValue();
        });

        TextField jointeamcode = new TextField("Create Code team");
        Button joiButton = new Button("Join team", event -> {
         jointeamcode.getValue();
        });

        add(btn1, btn2,jointeamcode, joiButton, codeteam, codeButton);
        setAlignItems(Alignment.CENTER);
    }
}
