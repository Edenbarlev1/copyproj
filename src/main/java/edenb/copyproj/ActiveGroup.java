package edenb.copyproj;

import java.util.List;

public class ActiveGroup  {

     private List<User> userList;
     private List<String> sessionList;
     private String groupCode;

    public ActiveGroup(List<User> userList, List<String> sessionList, String groupCode) {
        this.userList = userList;
        this.sessionList = sessionList;
        this.groupCode = groupCode;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<String> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<String> sessionList) {
        this.sessionList = sessionList;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    @Override
    public String toString() {
        return "ActiveGroup [userList=" + userList + ", sessionList=" + sessionList + ", groupCode=" + groupCode + "]";
    }

    

     
}
