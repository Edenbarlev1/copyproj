package edenb.copyproj;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import edenb.copyproj.PointService.CanvasChangeListener;
import edenb.copyproj.UserService.ButtonChangeListener;
import java.util.List;
import java.time.LocalDate;

@Route("canvas")
@PageTitle("Canvas")
public class CanvasPage extends VerticalLayout {

    private PointService pointService;
    private UserService userService;
    private DrawService drawService;

    // קבועים המציינים את רוחב וגובה הקנבס.
    private static final int CANVAS_WIDTH = 300;
    private static final int CANVAS_HEIGHT = 300;

    // משתנה פרטי מסוג Canvas המייצג את הקנבס.
    private Canvas ctx;

    // משתנים שמשמשים לניהול של פרטים  שם המשתמש, צבע הציור, וזיהוי הסשן הנוכחי.
    private String nameOfDraw;
    private LocalDate dateOfDraw;
    private String userName;
    private String colorState;
    private String currentSessionId;
    private boolean isDrawing = false;
    private boolean nameEntered = false;

    // משתנה סטטי שמציין האם כפתור ההתנתקות כבר נוסף לממשק המשתמש.
    private static boolean logoutButtonAdded = false;
  
    public CanvasPage(PointService pointService, UserService userService, DrawService drawService){
        this.pointService = pointService;
        this.userService = userService;
        this.drawService = drawService;

        //בודק אם משתמש ביצע התחברות אם לא מםנה אותו לדף התחברות
        // if (!login()) {
        //     UI.getCurrent().getPage().setLocation("/"); 
        //     return;
        // }
        userName = (String) VaadinSession.getCurrent().getSession().getAttribute("username");
        H2 h2 = new H2(userName + " Welcome to canvas page " );

         // יצירת קנבס והגדרת מאפיינים שונים כמו רוחב קו וצבע.
        ctx = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        ctx.setLineWidth(1);
        colorState = "red";
        ctx.setStrokeStyle(colorState);  
        ctx.getStyle().set("border", "3px solid orange");

            //הוספת כפתורים רק למשתתמש הראשון שמבצע התחברות
        if (!logoutButtonAdded) {
            Button logoutButton = new Button("exit&save", event -> {
                if(pointService.getAllPoints().isEmpty())
                {
                    Notification.show("You need to draw before saving.");
                }
                else if(!nameEntered){
                    Notification.show("Please enter a draw name.");
                }  
                else{
                 exitSave();
                 userService.gtfinishPage();
                }
            });
            TextField drawname = new TextField("draw name");
            Button nameButton = new Button(" Enter Name", event -> {
                
                nameOfDraw =  drawname.getValue();
                if(!drawname.isEmpty()){
                Notification.show("the name is added");
               drawname.setVisible(false);
               nameEntered = true;
               logoutButton.setEnabled(true);
                }
             else {
                Notification.show("Please enter a draw name");
            }
            });

            HorizontalLayout logoutLayout = new HorizontalLayout(logoutButton);
            add(logoutLayout,drawname,nameButton);

            //סימון שהכפתור נוסף      
           logoutButtonAdded = true; 
            }

        Button btn = new Button("Delete", event -> {
            colorState = "white";
            ctx.setLineWidth(1);
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
            ctx.moveTo(mx, my);
            pointService.addPoint(p);
        };

        ComponentEventListener<Canvas.MouseMoveEvent> mouseMoveListener = event -> {
            int mx = event.getMouseX();
            int my = event.getMouseY();
            
            if (isDrawing) {
                if (mx < 0 || mx > CANVAS_WIDTH || my < 0 || my > CANVAS_HEIGHT) {
                    isDrawing = false; 
                } else {
                    Point p = new Point(mx, my, userName, colorState);
                    ctx.lineTo(p.getX(), p.getY());
                    ctx.stroke();
                    pointService.addPoint(p);
                }}
        };

        ComponentEventListener<Canvas.MouseUpEvent> mouseUpListener = event -> {
            isDrawing = false;
            pointService.endDraw();
            pointService.DrawingPoints();
        };

        // הוספת המאזנים לאירועים על הקנבס.
        ctx.addMouseDownListener(mouseDownListener);
        ctx.addMouseMoveListener(mouseMoveListener);
        ctx.addMouseUpListener(mouseUpListener);

        HorizontalLayout buttonsLayout = new HorizontalLayout(btn, btn1, btn2, btn3);
        add(h2, buttonsLayout, ctx);
        setHorizontalComponentAlignment(Alignment.CENTER, buttonsLayout, ctx, h2);
    }

    
    @Override
    protected void onAttach(AttachEvent attachEvent) {
        
        super.onAttach(attachEvent);
        refreshCanvas();
        // קבלת המזהה הייחודי של הסשן הנוכחי
        currentSessionId = VaadinSession.getCurrent().getSession().getId();
        System.out.println("the sessionif is: " + currentSessionId);

        //בדיקה האם הסשן כבר נוסף
        if (!listenerFlagSetInSession()) {
            if(UI.getCurrent() != null){
            // הוספת מאזין לשינויים בקנבס
            pointService.addCanvasChangeListener(new CanvasChangeListener() {
                @Override
                public void onChange() {
                    System.out.println("\n>>>>>> CanvasChangeListener: " + userName + "\n");
                    UI ui = getUI().orElseThrow();
                    ui.access(() -> refreshCanvas());
                }
            });
        }
            //סימן שהסשן נוסף
            setListenerFlagInSession(true);
            System.out.println("Initializing canvas with existing points...");
        
        } else {
            System.out.println("Canvas listener already added.");
        }
    }

    //בדיקה האם הסשן כבר הוסף
    private boolean listenerFlagSetInSession() {
        return VaadinSession.getCurrent().getAttribute("canvasListenerAdded") != null;
    }
    //עידכון שהסשן הוסף
    private void setListenerFlagInSession(boolean value) {
        VaadinSession.getCurrent().setAttribute("canvasListenerAdded", value);
  //}

    userService.addbuttonChangeListener(new ButtonChangeListener(){
        @Override
        public void onChange1() {
            //System.out.println("\n>>>>>> CanvasChangeListener: " + userName + "\n");
            UI ui = getUI().orElseThrow();
            ui.access(() -> gotofinishPage());
        }
    });
}
    
    //// פונקציה לריענון הקנבס על פי השינויים שנעשו בו
    // private void refreshCanvas() {
    //             // קבלת כל הנקודות בקנבס
    //             List<Point> allPoints = pointService.getAllPoints();
    //             int pointsCount = allPoints.size();

    //             // בדיקה האם יש לפחות שתי נקודות כדי לבצע ציור
    //             if (pointsCount > 1) {
    //                 ctx.beginPath();
    //                 //מגיע לנקודת ההתחלה
    //                 Point firstPoint = allPoints.get(0);
    //                 ctx.moveTo(firstPoint.getX(), firstPoint.getY());
                    
    //                 // לולאה על כל הנקודות לצורך ציורם על הקנבס
    //                 for (int i = 1; i < pointsCount; i++) {
    //                     Point currentPoint = allPoints.get(i);
    //                     ctx.setStrokeStyle(currentPoint.getColor());
    //                     ctx.lineTo(currentPoint.getX(), currentPoint.getY());
    //                 }
    //                 ctx.stroke();
    //             }
    //         }

    // עדכון הצבע הנוכחי של הציור על הקנבס
    private void setColorState(String color) {
        colorState = color;
        ctx.setStrokeStyle(color);
    }
    
    // private boolean login() {
    //     userName = (String) VaadinSession.getCurrent().getSession().getAttribute("username");
    //     return userName != null;
    // }    

    private void exitSave()
   {  
      dateOfDraw = LocalDate.now();
      pointService.saveDrawing(nameOfDraw,dateOfDraw);
      pointService.clearPoints(); 
   }

private void refreshCanvas() {
    ctx.getElement().setAttribute("width", Integer.toString(CANVAS_WIDTH));
    ctx.getElement().setAttribute("height", Integer.toString(CANVAS_HEIGHT));
    
    //קבלת כל הנקודות שעכשיו צוירו
    List<Point> allPoints = pointService.getAllPoints();
    //ספירת הנקודות כמה נקודות סהכ יש
    int pointsCount = allPoints.size();

    // לולאת חזרה על כל הנקודות כדי לשרטט אותן על הקנבס
    for (int i = 0; i < pointsCount; i++) {
        // קבלת הנקודה הנוכחית
        Point currentPoint = allPoints.get(i);
        //קבלת צבע הקו
        ctx.setStrokeStyle(currentPoint.getColor());
        //ctx.setStrokeStyle(getDrawingColor());        

        //פתיחת נתיב חדש במקרה שהנקודת רחוקות זו מזו
        if (i == 0 || isSignificantGap(allPoints.get(i - 1), currentPoint)) {
            ctx.beginPath();
            ctx.moveTo(currentPoint.getX(), currentPoint.getY());
        }

        //שרטוט הנקודות
        ctx.lineTo(currentPoint.getX(), currentPoint.getY());
        ctx.stroke();
    }
}

// פונקציה שבודקת אם יש פער משמעותי בין נקודה קודמת לנקודה נוכחית
private boolean isSignificantGap(Point prevPoint, Point currentPoint) {

    //הגדרת המרחק המקסימלי שיכול להיות 
    double distanceThreshold = 10.0; 
    
    //חישוב  המרחק בין שתי נקודות במישור
    double distance = Math.sqrt(Math.pow(currentPoint.getX() - prevPoint.getX(), 2) +
                                Math.pow(currentPoint.getY() - prevPoint.getY(), 2));
           
    //בדיקה האם המרחק שהתקבל חורג מהמותר
    return distance > distanceThreshold;
}  

private void gotofinishPage(){
    UI.getCurrent().getPage().setLocation("/finishPage");
}
}