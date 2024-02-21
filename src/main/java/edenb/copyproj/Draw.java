package edenb.copyproj;

import java.util.List;

//import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Document;

//import com.vaadin.flow.component.template.Id;

@Document(collection="draws")
public class Draw {
        
    private double x;
    private double y;
    private String un;
    private String color;
    private List<Point> points;
    
    public Draw(double x, double y, String un, String color)
    {
        this.x = x;
        this.y = y;
        this.un = un;
        this.color = color;
    }
    
     public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    public String getUn() {
        return un;
    }

    public void setUn(String un) {
        this.un = un;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public List<Point> getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return un + ": (" + x + "," + y + "), Color: " + color;
    
}
}



    

