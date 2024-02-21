package edenb.copyproj;

 //import java.util.List;

 import org.springframework.data.mongodb.repository.MongoRepository;
 import org.springframework.stereotype.Repository;

 @Repository
 public interface DrawRepo extends MongoRepository<Draw, String>
 {
    //שמות המשתנים חייבים להיות אותו דבר יש לשים לב ךל extends
    // List<Draw> findByName(String adminGroup);
 }
