package edenb.copyproj;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PointService {

    private PointRepo pointRepo;
    private DrawRepo drawRepo;
    private ArrayList<CanvasChangeListener> listeners;
    private ArrayList<Point> pointsList;

    public interface CanvasChangeListener {
        void onChange();
    }

    public PointService(PointRepo pointRepo,DrawRepo drawRepo) {
        this.pointRepo = pointRepo;
        this.drawRepo = drawRepo;
        listeners = new ArrayList<CanvasChangeListener>();
        pointsList = new ArrayList<>();
    }
    
   public void endDraw() {
        System.out.println(pointsList);
        pointRepo.insert(pointsList);
        pointsList.clear();
   }

    public void addPoint(Point point) {
        pointsList.add(point);        
    }

    public List<Point> getAllPoints() {
        return pointRepo.findAll();
    }

    public void addCanvasChangeListener(CanvasChangeListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
            
        }
    }

    public void clearPoints() {
        pointsList.clear();
        pointRepo.deleteAll();
    }

    public ArrayList<Point> DrawingPoints() {
        synchronized(listeners) {
            for (CanvasChangeListener listener : listeners) {
                listener.onChange();
            }
            return pointsList;
        }
    }
    
public void saveDrawing(String drawName, LocalDate dateOfDraw) {
    List<Point> allPoints = pointRepo.findAll(); 
    Draw draw = new Draw(allPoints, drawName, dateOfDraw);
    drawRepo.insert(draw);
    System.out.println(draw);
}
   
}
