package edenb.copyproj;

//import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepo extends MongoRepository<Point, ObjectId>
{
    
}
