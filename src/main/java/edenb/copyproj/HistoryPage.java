package edenb.copyproj;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("history")
public class HistoryPage extends VerticalLayout {


    public HistoryPage() {
    H2 h2= new H2("hello to history page");
    add(h2);
    }
}
