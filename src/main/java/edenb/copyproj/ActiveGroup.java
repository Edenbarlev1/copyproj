package edenb.copyproj;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="active group")
public class ActiveGroup  {
     @Id
    private String name;
    //private String sessionList;
     private ArrayList<String> sessionList;
     private ArrayList<String> activeUser = new ArrayList<>();
     private Draw draw;
     
     public ActiveGroup()
     {
        
     }
     public ActiveGroup(String activeUser, String session, String nameGroup) {
        this.activeUser = new ArrayList<>();
        this.activeUser.add(activeUser);
        this.sessionList = new ArrayList<>();
        this.sessionList.add(session); // Corrected to use this.sessionList
        this.name = nameGroup;
        this.draw = new Draw();
    }
    

    public ArrayList<String> getActiveUser() {
        return activeUser;
    }

    public void addActiveUser(String activeUser) 
    {
        this.activeUser.add(activeUser);
    }

    public List<String> getSessionList() {
        return sessionList;
    }

    public void addSessionList(String session) {
        if (this.sessionList == null) {
            this.sessionList = new ArrayList<>();
        }
        this.sessionList.add(session);
    }
    

    public String getNameGroup() {
        return name;
    }

    public void setNameGroup(String nameGroup) {
        this.name = nameGroup;
    }

    public void removeActiveUser(String username) {
        activeUser.remove(username);
    }
    

    @Override
    public String toString() {
        return "ActiveGroup [sessionList=" + sessionList + ", activeUser=" + activeUser + ", nameGroup=" + name
                + "]";
    }

    

     
}
