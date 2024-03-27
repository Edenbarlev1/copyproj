package edenb.copyproj;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepo extends MongoRepository<ActiveGroup, String>
{
    public ActiveGroup findByName(String Name);
    
}
