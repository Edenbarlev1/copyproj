package edenb.copyproj;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.List;

@Route(value = "/history", layout = AppMainLayout.class)
@PageTitle("History")
public class HistoryPage extends VerticalLayout {

    private final DrawService drawService;
    private static final int CANVAS_WIDTH = 300;
    private static final int CANVAS_HEIGHT = 300;

    public HistoryPage(DrawService drawService) {
        this.drawService = drawService;
        
        H1 h1 = new H1("welcome to history page");
        add(h1);
        //קבלת כל הנקודות של הציור
        List<Draw> savedDrawings = drawService.getAllDraws();
        Button btn = new Button("Delete", event -> {
            drawService.deleteDraw();
             UI.getCurrent().getPage().reload();
        });
        // לולאה שעוברת על כל הציורים
        for (Draw savedDrawing : savedDrawings) {
            // יצירת  קנבס עבור כל רישום שנשמר
            Canvas canvas = createCanvas(savedDrawing);
            add(new H2("Draw name:"+savedDrawing.getNameofdraw() + "\n Date:" + savedDrawing.getdateOfDraw()));
            add(canvas, btn);
        }
    }
    //פונקציה ליצירת קנבס חדש
    private Canvas createCanvas(Draw savedDrawing) {
        // יצירת הקנבס והגדרת מסגרת 
        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
          canvas.getStyle().set("border", "3px solid blue"); 
        // שירטוט הציור שנשמר
        drawSavedDrawing(canvas, savedDrawing);
        return canvas;
    }

private void drawSavedDrawing(Canvas canvas, Draw savedDrawing){    
    //קבלת כל הנקודות שעכשיו צוירו
    List<Point> points = savedDrawing.getPointList();

    // לולאת חזרה על כל הנקודות כדי לשרטט אותן על הקנבס
    for (int i = 0; i < points.size()-1; i++) {
        // קבלת הנקודה הנוכחית
        Point currentPoint = points.get(i);
        //קבלת צבע הקו
        canvas.setStrokeStyle(currentPoint.getColor());

        //פתיחת נתיב חדש במקרה שהנקודת רחוקות זו מזו
        if (i == 0 || isSignificantGap(points.get(i - 1), currentPoint)) {
            canvas.beginPath();
            canvas.moveTo(currentPoint.getX(), currentPoint.getY());
        }
        //שרטוט הנקודות
        canvas.lineTo(currentPoint.getX(), currentPoint.getY());
        canvas.stroke();
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

}







