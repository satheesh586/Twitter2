package services;

import repositories.RepositoryTrendingHashTags;
import models.TrendingHashTags;
import play.db.ebean.Model;
import twitter4j.HashtagEntity;
import twitter4j.Status;


/**
 * Created by satheesh on 5/9/14.
 */
public class TopTrendingHashTags extends Model {



     public static void checkDataBaseToUpdate(int entityId,Status tweetInfo){

         TrendingHashTags trendingHashTags;

         HashtagEntity[] hashtagEntities=tweetInfo.getHashtagEntities();

         for (HashtagEntity hashtagEntity:hashtagEntities) {
//             System.out.println("hashTag******"+hashtagEntity.getText()+"*****************************");
             trendingHashTags=RepositoryTrendingHashTags.isPresent(hashtagEntity.getText(),entityId);

             if ( trendingHashTags!=null && (tweetInfo.getCreatedAt().getTime()-trendingHashTags.getTimestamp().getTime()<60000)) {
                   RepositoryTrendingHashTags.upDateHashTagScore(hashtagEntity.getText(),entityId);
             }
             else{
                 RepositoryTrendingHashTags.insert(getModelObject(hashtagEntity.getText(), entityId,tweetInfo));
             }
         }


     }


     public static TrendingHashTags getModelObject(String hashtag, int entityId, Status tweetInfo){
         java.sql.Timestamp timestamp =new java.sql.Timestamp(tweetInfo.getCreatedAt().getTime()-tweetInfo.getCreatedAt().getTime()%60000);
           return new TrendingHashTags(entityId,hashtag,1, timestamp);
     }




}
