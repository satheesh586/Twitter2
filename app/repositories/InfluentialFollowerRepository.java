package repositories;

import com.avaje.ebean.Ebean;
import models.InfluentialFollower;
import models.ResultJsonObjects.JsonInfluentialFollower;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satheesh on 16/9/14.
 */
public class InfluentialFollowerRepository {

    public static List<InfluentialFollower> getList(int entityId, long id){

        return InfluentialFollower.find.where().eq("entityid", entityId).eq("userid",id).order().desc("timestamp").findList();

    }

    public static void save(InfluentialFollower influentialfollower) {
        Ebean.save(influentialfollower);
    }

    public static void update(InfluentialFollower influentialFollower,double score) {
        influentialFollower.setScore(influentialFollower.getScore()+score);
    }

    public static List<JsonInfluentialFollower> getInfluentialFollower(int entityId){

        List<JsonInfluentialFollower> jsonInfluentialFollowers=new ArrayList<>();

        for(InfluentialFollower influentialFollower: getAllInfluentialFollower(entityId))
        {
            jsonInfluentialFollowers.add(new JsonInfluentialFollower(influentialFollower.getScreenName(),influentialFollower.getScore()
                    ,influentialFollower.getFollowercount()));

        }

        return jsonInfluentialFollowers;
    }

    private static List<InfluentialFollower> getAllInfluentialFollower(int entityId) {

        return InfluentialFollower.find.where().eq("entityid",entityId).order().desc("score").setMaxRows(10).findList();
    }

}
