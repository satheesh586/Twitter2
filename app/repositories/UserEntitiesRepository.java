package repositories;

import models.Query;
import models.User;
import models.UserEntities;
import play.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubham on 18/9/14.
 */
public class UserEntitiesRepository {
    public static void add(String screenName,int entityId) {
        Logger.info("Adding "+screenName+" "+entityId);
        User user=User.find.byId(screenName);
        Query query=Query.find.byId(entityId);
        UserEntities userEntities=new UserEntities(user,query);
        userEntities.save();
    }
    public static List<Query> getEntities(String screenName) {
//        User user=User.find.byId(screenName);
        List<UserEntities> userEntitiesList = UserEntities.find.where().eq("screenName",screenName).findList();
        List<Query> queries=new ArrayList<>();
        for(UserEntities userEntities:userEntitiesList) queries.add(userEntities.query);
        return queries;
    }
}
