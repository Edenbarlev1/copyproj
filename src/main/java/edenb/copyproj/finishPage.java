package edenb.copyproj;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("/finishPage")
@PageTitle("Finish Page")
public class finishPage extends VerticalLayout {

    H2 h2 = new H2("The painting is finished and saved, thank you");
    Button btn1 = new Button("history", event -> {
            UI.getCurrent().getPage().setLocation("/history");
        });

        Button btn2 = new Button("logout", event -> {
            VaadinSession.getCurrent().getSession().invalidate();
            UI.getCurrent().getPage().setLocation("/ ");
        });

        public finishPage(){
            
         HorizontalLayout buttonsLayout = new HorizontalLayout(btn1, btn2);
        add(h2, buttonsLayout);
}
}
