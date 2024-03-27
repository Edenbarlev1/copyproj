package edenb.copyproj;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private GroupRepo groupRepo;
    private UserRepo userRepo;

    private ArrayList<ActiveGroup> groupsList;
    private ArrayList<OnlineUserChangeListener> listeners;

    public interface OnlineUserChangeListener {
        public void onChange2();
    }

    public GroupService(GroupRepo groupRepo, UserRepo userRepo) {
        this.groupRepo = groupRepo;
        this.userRepo = userRepo;

        listeners = new ArrayList<OnlineUserChangeListener>();
    }

    public void addOnlineChangeListener(OnlineUserChangeListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public ArrayList<ActiveGroup> updateOnlineliseteners() {
        synchronized (listeners) {
            for (OnlineUserChangeListener listener : listeners) {
                listener.onChange2();
            }
            return groupsList;
        }
    }

    public void removeOnlineChangeListener() {
        synchronized (listeners) {
            listeners.clear(); // Remove all listeners
        }
    }

     public ActiveGroup searchUserById(String id) {
        Optional<ActiveGroup> optionalGroup = groupRepo.findById(id);
        return optionalGroup.orElse(null);
    }

    public void addUserAndSessionToGroup(String username, String session, String id) {

        Optional<ActiveGroup> optionalGroup = groupRepo.findById(id);
        System.out.println("2");
        if (optionalGroup.isPresent()) {
            ActiveGroup activeGroup = optionalGroup.get();
            activeGroup.addActiveUser(username);
            activeGroup.addSessionList(session);
            groupRepo.save(activeGroup);
        } else {
            System.out.println("not find!");
        }
    }

    public void addUserAndSessionToNewGroup(String username, String session, String groupName) {

        ActiveGroup group = new ActiveGroup(username, session, groupName);
        groupRepo.insert(group);
    }

    public boolean isGroupNameExists(String id) {
        ActiveGroup group = groupRepo.findByName(id);
        if(group != null)
        {
            return true;
        }
        return false;
    }

    public ArrayList<ActiveGroup> getAllGroups()
    {
        List<ActiveGroup> groupList = groupRepo.findAll();
        ArrayList<ActiveGroup> arrayList = new ArrayList<>(groupList);
        return arrayList;
    }

    public void removeUserFromActiveGroup(String userName) {
        ActiveGroup activeGroup = groupRepo.findByName(userName);
        if (activeGroup != null) {
            groupRepo.delete(activeGroup);
            groupRepo.save(activeGroup);
        }
    }

    public void deleteGroup(String groupName) {
        ActiveGroup group = groupRepo.findByName(groupName);
        if (group != null) {
            groupRepo.delete(group);
        }
    }
    
    // public void saveDrawForGroup(String groupName) {
    //     ActiveGroup group = groupRepo.findByName(groupName);
    //     if (group != null) {
            
    //     }
    // }
    
    
}
