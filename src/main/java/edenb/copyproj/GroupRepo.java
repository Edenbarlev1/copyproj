package edenb.copyproj;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepo extends MongoRepository<Point, ObjectId>
{
    
}
