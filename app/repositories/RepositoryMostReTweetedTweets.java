package repositories;

import com.avaje.ebean.Ebean;
import models.MostReTweetedTweets;
import models.ResultJsonObjects.JsonMostreTweeted;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepak on 5/9/14.
 */
public class RepositoryMostReTweetedTweets implements Serializable{


    public static boolean isPresent(int entityId, long parentId) {
       int resultCount = MostReTweetedTweets.find.where().eq("entityId",entityId).eq("parenttweetid",parentId).findRowCount();
        if(resultCount >= 1)
            return true;
        return false;
    }

    public static MostReTweetedTweets getMinTweetCount(int entityId) {
       return getAllRows(entityId).get(0);
    }

    public static void insert(MostReTweetedTweets mostretweetedtweets) {
        Ebean.save(mostretweetedtweets);
    }

    public static void delete(Long tweetId) {
        MostReTweetedTweets.find.ref(tweetId).delete();
    }

    public static List<MostReTweetedTweets> getAllRows(int entityId){
        return MostReTweetedTweets.find.where().eq("entityId", entityId).order().desc("retweetcount").findList();
    }

    public static int getNumberOfRows(int entityId){
        return getAllRows(entityId).size();
    }

    public static List<JsonMostreTweeted> getListOfMostReTweetedTweets(int entityId){

        List<JsonMostreTweeted> jsonMostreTweeteds = new ArrayList<JsonMostreTweeted>();

        List<MostReTweetedTweets> mostReTweetedTweets=getAllRows(entityId);
        for (MostReTweetedTweets mostretweetedtweets:mostReTweetedTweets)
        {
            jsonMostreTweeteds.add(new JsonMostreTweeted(mostretweetedtweets.getTweetText(),mostretweetedtweets.getReTweetCount()));
        }
       return jsonMostreTweeteds;
    }

}
