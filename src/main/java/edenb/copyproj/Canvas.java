 package edenb.copyproj;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.shared.Registration;

//תג שמשמש ליצירת אלמנט גרפי בדף HTML
@Tag("canvas")

//מחלקת קנבס שתיהיה מסוג קומפוננט 
public class Canvas extends Component{

    // קונסטרקטור למחלקת קנבס עם פרמטרים של אורך ורוחב הקנבס
    public Canvas(int width, int height) {

      // הגדרת האורך והרוחב של ה־canvas באמצעות הגדרת מאפייני ה־width וה־height
        getElement().setAttribute("width", String.valueOf(width));
        getElement().setAttribute("height", String.valueOf(height));

        // אתחול של context באמצעות JavaScript
        getElement().executeJs("this.context = this.getContext('2d');");
    }

   // הוספת האזנה לאירוע לחיצת העכבר
    public Registration addMouseDownListener(ComponentEventListener<MouseDownEvent> listener) {
        return addListener(MouseDownEvent.class, listener);
    }

    public Registration addMouseUpListener(ComponentEventListener<MouseUpEvent> listener) {
        return addListener(MouseUpEvent.class, listener);
    }

    public Registration addMouseMoveListener(ComponentEventListener<MouseMoveEvent> listener) {
        return addListener(MouseMoveEvent.class, listener);
    }

//אנוטציה שאומרת לVAADIN שהרכיב יציין את עצמו כמאזין לארוע מסוים 
    @DomEvent("mousedown")
    public static class MouseDownEvent extends ComponentEvent<Canvas> {

        private int mouseX, mouseY;

        // קונסטרקטור לאירוע לחיצת העכבר
        public MouseDownEvent(Canvas source, boolean fromClient,
                              @EventData("event.offsetX") int mouseX,
                              @EventData("event.offsetY") int mouseY) {
            super(source, fromClient);
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }

        public int getMouseX() {
            return mouseX;
        }

        public int getMouseY() {
            return mouseY;
        }
    }

    @DomEvent("mouseup")
    public static class MouseUpEvent extends ComponentEvent<Canvas> {
        private int mouseX, mouseY;

        public MouseUpEvent(Canvas source, boolean fromClient,
                            @EventData("event.offsetX") int mouseX,
                            @EventData("event.offsetY") int mouseY) {
            super(source, fromClient);
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }

        public int getMouseX() {
            return mouseX;
        }

        public int getMouseY() {
            return mouseY;
        }
    }

    @DomEvent("mousemove")
    public static class MouseMoveEvent extends ComponentEvent<Canvas> {
        private int mouseX, mouseY;

        public MouseMoveEvent(Canvas source, boolean fromClient,
                              @EventData("event.offsetX") int mouseX,
                              @EventData("event.offsetY") int mouseY) {
            super(source, fromClient);
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }

        public int getMouseX() {
            return mouseX;
        }

        public int getMouseY() {
            return mouseY;
        }
    }

    // הגדרת מתודות להגדרת המאפיינים של ה־canvas
    //הגדרת עובי הקו
   public void setLineWidth(int width) {
      getElement().executeJs("this.getContext('2d').lineWidth = $0", width);
  }
  //הגדרת צבע הקו
   public void setStrokeStyle(String color) {
      getElement().executeJs("this.getContext('2d').strokeStyle = $0", color);
  }
  //איפוס הלוח
    public void clearRect(int i, int j, int canvasWidth, int canvasHeight) {
        getElement().callJsFunction("clearRect", i, j, canvasWidth, canvasHeight);
    }
    //הגדרת התחלה של קו
    public void beginPath() {
        getElement().executeJs("this.context.beginPath()");
    }

    public void moveTo(double x, double y) {
        getElement().executeJs("this.context.moveTo($0, $1)", x, y);
    }

    public void lineTo(double x, double y) {
        getElement().executeJs("this.context.lineTo($0, $1)", x, y);
    }

    public void stroke() {
        getElement().executeJs("this.context.stroke()");
    }
}
