package edenb.copyproj;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import edenb.copyproj.PointService.CanvasChangeListener;

//import java.util.ArrayList;
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.io.IOException;
import java.util.List;

//import javax.imageio.ImageIO;

@Route("canvas")
public class CanvasPage extends VerticalLayout {

    private PointService pointService;
    //private DrawService drawService;
    private static final int CANVAS_WIDTH = 300;
    private static final int CANVAS_HEIGHT = 300;
    private Canvas ctx;
    private String userName;
    private String colorState;
    private String currentSessionId;
    private boolean isDrawing = false;
    private static boolean logoutButtonAdded = false;
    //private ArrayList<Point> saveYourCanvas;
   // private ArrayList<user> saveYourCanvas;

    public CanvasPage(PointService pointService){//, DrawService DrawService) {
        this.pointService = pointService;
        //this.drawService = DrawService;
        //saveYourCanvas = new ArrayList<>();

        if (!login()) {
            UI.getCurrent().getPage().setLocation("/"); 
            return;
        }

        H2 h2 = new H2("Welcome to canvas page");
        ctx = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        ctx.setLineWidth(1);
        colorState = "red";
        ctx.setStrokeStyle(colorState);  
        ctx.getStyle().set("border", "3px solid black");

        // Add logout button only if it hasn't been added before
        if (!logoutButtonAdded) {
            Button logoutButton = new Button("exit&save", event -> {
                 //saveDrawingToDatabase();
                 logout();
                
                
            });

            HorizontalLayout logoutLayout = new HorizontalLayout(logoutButton);
            add(logoutLayout);
            logoutButtonAdded = true; // Set flag to indicate that the logout button has been added
        }

        Button btn = new Button("Delete", event -> {
            colorState = "white";
            ctx.setLineWidth(5);
            setColorState("white");
        });

        Button btn1 = new Button("Yellow", event -> {
            setColorState("yellow");
        });
        
        Button btn2 = new Button("Blue", event -> {
            setColorState("blue");
        });
        
        Button btn3 = new Button("Gray", event -> {
            setColorState("gray");
        });

        ComponentEventListener<Canvas.MouseDownEvent> mouseDownListener = event -> {
            int mx = event.getMouseX();
            int my = event.getMouseY();
            isDrawing = true;
            ctx.beginPath();
            Point p = new Point(mx, my, userName, colorState);
           // p = new Point(mx, my, userName, colorState);
            
           // saveYourCanvas.add(new Point(mx, my,userName,colorState));
            ctx.moveTo(mx, my);
            pointService.startDraw(p);
            //Draw d = new Draw(mx, my, userName, colorState);
            //drawService.startDraw(d);

        };

        ComponentEventListener<Canvas.MouseMoveEvent> mouseMoveListener = event -> {
            int mx = event.getMouseX();
            int my = event.getMouseY();
            if (isDrawing) {
                Point p = new Point(mx, my, userName, colorState);
               // saveYourCanvas.add(new Point(mx, my,userName,colorState));

                ctx.lineTo(p.getX(), p.getY());
                ctx.stroke();
                pointService.addPoint(p);
               // Draw d = new Draw(mx, my, userName, colorState);
           // drawService.addPoint(d);
            }
        };

        ComponentEventListener<Canvas.MouseUpEvent> mouseUpListener = event -> {
            int mx = event.getMouseX();
            int my = event.getMouseY();
            Point p = new Point(mx, my, userName, colorState);
            //saveYourCanvas.add(new Point(mx, my,userName,colorState));

            isDrawing = false;
            pointService.endDraw(p);
            pointService.DrawingPoints();
            pointService.clearPoints();

           // Draw d = new Draw(mx, my, userName, colorState);
           // drawService.endDraw(d);
        };

        // Add the mouse listeners to the canvas
        ctx.addMouseDownListener(mouseDownListener);
        ctx.addMouseMoveListener(mouseMoveListener);
        ctx.addMouseUpListener(mouseUpListener);

        HorizontalLayout buttonsLayout = new HorizontalLayout(btn, btn1, btn2, btn3);
        add(h2, buttonsLayout, ctx);
        setHorizontalComponentAlignment(Alignment.CENTER, buttonsLayout, ctx, h2);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        currentSessionId = VaadinSession.getCurrent().getSession().getId();
        System.out.println("the sessionif is: " + currentSessionId);
        // Check if the listener flag is set in the session
        if (!listenerFlagSetInSession()) {
            pointService.addCanvasChangeListener(new CanvasChangeListener() {
                @Override
                public void onChange() {
                    System.out.println("\n>>>>>> CanvasChangeListener: " + userName + "\n");
                    UI ui = getUI().orElseThrow();
                    ui.access(() -> refreshCanvas());
                }
            });
            // Set the flag in the session to indicate that the listener has been added
            setListenerFlagInSession(true);
        } else {
            System.out.println("Canvas listener already added.");
        }
    }

    private boolean listenerFlagSetInSession() {
        return VaadinSession.getCurrent().getAttribute("canvasListenerAdded") != null;
    }

    private void setListenerFlagInSession(boolean value) {
        VaadinSession.getCurrent().setAttribute("canvasListenerAdded", value);
    }

    private void refreshCanvas() {
                List<Point> allPoints = pointService.getAllPoints();
                int pointsCount = allPoints.size();
        
                if (pointsCount > 1) {
                    ctx.beginPath();
                    Point firstPoint = allPoints.get(0);
                    ctx.moveTo(firstPoint.getX(), firstPoint.getY());
        
                    for (int i = 1; i < pointsCount; i++) {
                        Point currentPoint = allPoints.get(i);
                        ctx.setStrokeStyle(currentPoint.getColor());
                        ctx.lineTo(currentPoint.getX(), currentPoint.getY());
                    }
                    ctx.stroke();
                }
            }

    private void setColorState(String color) {
        colorState = color;
        ctx.setStrokeStyle(color);
    }

    private boolean login() {
        userName = (String) VaadinSession.getCurrent().getSession().getAttribute("username");
        return userName != null;
    }    

    private void logout()
   {
      VaadinSession.getCurrent().getSession().invalidate();
      UI.getCurrent().getPage().setLocation("/history");
      Notification.show("the host leave&save");
      //UI.getCurrent().getPage().reload();
   }

//    private void routeToHPage()
//    {
//       UI.getCurrent().getPage().setLocation("/history");
//    }


    // private void saveDrawingToDatabase() {
    //     List<Draw> savedDrawings = drawService.getSavedDrawing();

    //     // End each drawing
    //     for (Draw draw : savedDrawings) {
    //         drawService.endDraw(draw);
    //     }
    
    //     // Redirect to the history page
    //     UI.getCurrent().navigate("history");
    // }
}