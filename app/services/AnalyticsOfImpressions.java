package services;

import models.Impressions;
import repositories.ImpressionsRepository;
import twitter4j.Status;

import java.io.IOException;


/**
 * Created by shubham on 15/9/14.
 */
public class AnalyticsOfImpressions {

   /* static Timestamp timestamp= new Timestamp(new Date().getTime());*/


    public static void checkDataBaseToUpdate(Status tweetInfo,int entityId) throws IOException, ClassNotFoundException {
        if(ImpressionsRepository.isPresent(entityId)) {
            if (tweetInfo.getCreatedAt().getTime() - ImpressionsRepository.getLargestTimestamp(entityId).getTime() < 60000) {
                ImpressionsRepository.upDateRepository(entityId, tweetInfo.getUser().getFollowersCount());
            }
            else {
                ImpressionsRepository.insert(getModelObject(tweetInfo, entityId));
            }
        }

        else {
            ImpressionsRepository.insert(getModelObject(tweetInfo, entityId));
        }
    }

    public static Impressions getModelObject(Status tweetInfo,int entityId){
        java.sql.Timestamp timestamp =new java.sql.Timestamp(tweetInfo.getCreatedAt().getTime()-tweetInfo.getCreatedAt().getTime()%60000);
        return new Impressions(entityId,tweetInfo.getUser().getFollowersCount(),timestamp);

    }
}
