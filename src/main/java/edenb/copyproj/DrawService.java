 package edenb.copyproj;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DrawService 
{    
    
    private DrawRepo drawRepo;
    private ArrayList<Draw> drawsList; 

 public DrawService(DrawRepo drawRepo) 
    {
        this.drawRepo = drawRepo;
        drawsList = new ArrayList<>();
    }

    public void startDraw(Draw draw )
   {
        drawsList = new ArrayList<>();
        drawsList.add(draw);
   }

   public void endDraw(Draw draw)
   {
        drawsList.add(draw);
        //System.out.println(pointsList);
        drawRepo.insert(drawsList);
   }

    public void addPoint(Draw draw) 
    {
        drawsList.add(draw);        
    }

    public List<Draw> getSavedDrawing() {
        return drawRepo.findAll(); // Assuming getAllDraws() returns a list of saved drawings
    }
    
}



