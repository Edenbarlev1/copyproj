package edenb.copyproj;

import org.springframework.stereotype.Service;

@Service
public class GroupService {
    
    private GroupRepo groupRepo;

    public GroupService(GroupRepo groupRepo) {
        this.groupRepo = groupRepo;
    }

    
}