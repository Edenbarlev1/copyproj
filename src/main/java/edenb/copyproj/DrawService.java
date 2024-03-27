package edenb.copyproj;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DrawService {

    private DrawRepo drawRepo;
    private ArrayList<Draw> drawsList;
   private ArrayList<String> userList;
   

    public DrawService(DrawRepo drawRepo) {
        this.drawRepo = drawRepo;
        userList = new ArrayList<>();  
    }
    
    //קבלת כל הנקודות שנשמרו מהמאגר נתונים
    public List<Draw> getAllDraws() 
    {
      return drawRepo.findAll();        
    }
  
  public void deleteDraw()
  {
    drawRepo.deleteAll();
  }

  public void addUserToDraw(String user)
  {
    userList.add(user);
  }
}
