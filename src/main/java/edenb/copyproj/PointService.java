package edenb.copyproj;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.vaadin.flow.server.VaadinSession;

@Service
public class PointService {

    private PointRepo pointRepo;
    private DrawRepo drawRepo;
    private GroupRepo groupRepo;

    private ArrayList<CanvasChangeListener> listeners;
    private ArrayList<Point> pointsList;

    public interface CanvasChangeListener {
        void onChange();
    }

    public PointService(PointRepo pointRepo,DrawRepo drawRepo,GroupRepo groupRepo) {
        this.pointRepo = pointRepo;
        this.drawRepo = drawRepo;
        this.groupRepo = groupRepo;
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

    public ArrayList<Point> DrawingPoints() {
        synchronized(listeners) {
            for (CanvasChangeListener listener : listeners) {
                listener.onChange();
            }
            return pointsList;
        }
    } 

    public void removeCanvasChangeListener() {
        synchronized(listeners) {
            listeners.clear(); // Remove all listeners
        }
    }
    
    public void clearPoints() {
        pointsList.clear();
        pointRepo.deleteAll();
    }
    
// public void saveDrawing(String drawName, LocalDate dateOfDraw, List<ActiveGroup> userList) {
//     List<Point> allPoints = pointRepo.findAll(); 
//     // List<String> allUser = drawRepo.findAll();
//     Draw draw = new Draw(allPoints, drawName, dateOfDraw,userList);
//     drawRepo.insert(draw);
//     System.out.println(draw);
// }

// public void saveDrawing(String drawName, LocalDate dateOfDraw, String groupName) {
//     List<Point> allPoints = pointRepo.findAll();
//     ActiveGroup group = groupRepo.findByName(groupName); // Get active group by name
//     Draw draw = new Draw(allPoints, drawName, dateOfDraw, List.of(group)); // Wrap the group in a list
//     drawRepo.insert(draw);
//     System.out.println(draw);
// }

public void saveDrawing(String drawName, LocalDate dateOfDraw, String groupName) {
    // Method implementation
        List<Point> allPoints = pointRepo.findAll();
        String userName = (String) VaadinSession.getCurrent().getSession().getAttribute("username");
        ActiveGroup group = groupRepo.findByName(userName); // Get active group by user name
        if (group != null) {
            Draw draw = new Draw(allPoints, drawName, dateOfDraw, List.of(group)); // Wrap the group in a list
            drawRepo.insert(draw);
            System.out.println(draw);
        } else {
            System.out.println("User group not found.");
        }
    }
   
}
