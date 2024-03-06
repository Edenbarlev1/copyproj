package edenb.copyproj;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="draws")
public class Draw {
        
    private LocalDate dateOfDraw;
    private List<Point> pointList;
    private List<User> userList;
    private String nameOfDraw;

     public Draw(List<Point> pointList,String nameOfDraw,LocalDate dateOfDraw) {
        this.nameOfDraw = nameOfDraw;
        this.pointList = pointList;
        this.dateOfDraw = dateOfDraw;
    }

    public String getNameOfDraw() {
        return nameOfDraw;
    }

    public void setNameOfDraw(String nameOfDraw) {
        this.nameOfDraw = nameOfDraw;
    }

    public Draw() {
    }

    public LocalDate getdateOfDraw() {
        return dateOfDraw;
    }

    public void setProductiondate(LocalDate dateOfDraw) {
        this.dateOfDraw = dateOfDraw;
    }

    public List<Point> getPointList() {
        return pointList;
    }

    public void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getNameofdraw() {
        return nameOfDraw;
    }

    public void setNameofdraw(String nameofdraw) {
        this.nameOfDraw = nameofdraw;
    }

    @Override
    public String toString() {
        return "Draw [Productiondate=" + dateOfDraw + ", pointList=" + pointList + ", userList=" + userList
                + ", nameofdraw=" + nameOfDraw + "]";
    }  
}



    

