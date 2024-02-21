package edenb.copyproj;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PointService {

    private PointRepo pointRepo;
    private ArrayList<CanvasChangeListener> listeners;
    private ArrayList<Point> pointsList;

    public interface CanvasChangeListener {
        void onChange();
    }

    public PointService(PointRepo pointRepo) {
        this.pointRepo = pointRepo;
        listeners = new ArrayList<CanvasChangeListener>();
        pointsList = new ArrayList<>();
    }
    
   public void startDraw(Point point) {
        pointsList = new ArrayList<>();
        pointsList.add(point);
   }

   public void endDraw(Point point) {
        pointsList.add(point);
        System.out.println(pointsList);
        pointRepo.insert(pointsList);
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
}
