package services;

import models.InfluentialFollower;
import repositories.InfluentialFollowerRepository;
import twitter4j.Status;

import java.util.List;

/**
 * Created by satheesh on 16/9/14.
 */
public class InfluentialFollowerService {

    public static void upDateDataBase(int entityId, Status tweetInfo){

        double score =0.2*tweetInfo.getRetweetCount()+0.6*tweetInfo.getUser().getFollowersCount()/*+0.2*tweetInfo.getFavoriteCount()*/;
        System.out.println(tweetInfo.getUser().getBiggerProfileImageURLHttps());

       List<InfluentialFollower> influentialfollower= InfluentialFollowerRepository.getList(entityId, tweetInfo.getUser().getId());
           if(influentialfollower.size()!=0 && tweetInfo.getCreatedAt().getTime()-influentialfollower.get(0).getTimestamp().getTime()<3600000)
           {
               InfluentialFollowerRepository.update(influentialfollower.get(0),score);
           }

           else
           {
               InfluentialFollowerRepository.save(getModelObject(entityId,tweetInfo,score));
             }



    }


    public static InfluentialFollower getModelObject(int entityId, Status tweetInfo,double score) {
        java.sql.Timestamp timestamp =new java.sql.Timestamp(tweetInfo.getCreatedAt().getTime()-tweetInfo.getCreatedAt().getTime()%60000);
        return new InfluentialFollower(tweetInfo.getUser().getId(),entityId,tweetInfo.getUser().getScreenName(),score,timestamp,
                tweetInfo.getUser().getFollowersCount());
    }
}
