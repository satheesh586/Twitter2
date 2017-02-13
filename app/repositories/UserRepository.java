package repositories;

import models.User;

import java.util.List;

/**
 * Created by shubham on 17/9/14.
 */
public class UserRepository {
    public static List<User> getAllUsers(){
        return User.find.all();
    }

    public static void addUser(User user){   //will take some parameters as well
        user.save();
    }

    public static boolean isPresent (String screenName){
        User user=User.find.byId(screenName);
        return (user!=null);
    }

    public static User getUser(String screenName){
        return User.find.byId(screenName);
    }
}
