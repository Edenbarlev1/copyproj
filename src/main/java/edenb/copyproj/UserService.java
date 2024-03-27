package edenb.copyproj;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {
   private UserRepo userRepo;
   private ArrayList<ButtonChangeListener> listeners;

   public interface ButtonChangeListener
   {
      public void onChange1();
   }

   public UserService(UserRepo userRepo) {
      this.userRepo = userRepo;
      listeners = new ArrayList<ButtonChangeListener>();
   }

   public void addUser(User user) {
      userRepo.insert(user);
   }

   public List<User> getAllUsers() {
      return userRepo.findAll();
   }

   public boolean isUserExists(String un) {
      User user = userRepo.findByUsername(un);
      return user != null;
  }
   
   public void addbuttonChangeListener(ButtonChangeListener listener)
   {
      synchronized (listeners)
      {
         listeners.add(listener);
      }
   }
    public void gtfinishPage() {
        synchronized(listeners) {
            for (ButtonChangeListener listener : listeners) {
                listener.onChange1();
            }
        }
    }

   
    public User getUserByID(String username) {

      return userRepo.findByUsername(username);
   }


}



