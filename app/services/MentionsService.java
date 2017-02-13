package services;

import models.Mentions;
import repositories.MentionsRepository;
import twitter4j.Status;

import java.util.List;

/**
 * Created by shubham on 16/9/14.
 */
public class MentionsService {
    public static void upDateDataBase(int entityId, Status tweetInfo){
        List<Mentions> mentionses= MentionsRepository.getMention(entityId);
        if(mentionses.size()!=0 && tweetInfo.getCreatedAt().getTime()-mentionses.get(0).getTimeStamp().getTime()<60000)
            MentionsRepository.upDateRepository(entityId);
        else
            MentionsRepository.save(getModelObject(entityId,tweetInfo));


    }

    private static Mentions getModelObject(int entityId, Status tweetInfo) {
        java.sql.Timestamp timestamp =new java.sql.Timestamp(tweetInfo.getCreatedAt().getTime()-tweetInfo.getCreatedAt().getTime()%60000);
        return new Mentions(entityId,1,timestamp);
    }
}
