package services;

import repositories.RepositoryMostReTweetedTweets;
import models.MostReTweetedTweets;
import twitter4j.Status;

/**
 * Created by satheesh on 1/9/14.
 */
class TopFiveMostReTweedPosts {

    public static void update(int entityId,Status tweetInfo){

        if(tweetInfo.getRetweetedStatus()!=null) {
            if(!RepositoryMostReTweetedTweets.isPresent(entityId,tweetInfo.getRetweetedStatus().getId())){
                checkToInsert(entityId,tweetInfo);
            }
        }
        else{
            checkToInsert(entityId,tweetInfo);
        }
    }


    public static void checkToInsert(int entityId,Status tweetInfo)
    {
        if (!isNumberOfRowsLessThanFive(entityId))
            updateDatabase(entityId,tweetInfo);
        else
            RepositoryMostReTweetedTweets.insert(getModelObject(tweetInfo,entityId));
    }

    public static boolean isNumberOfRowsLessThanFive(int entityId){

        if(RepositoryMostReTweetedTweets.getNumberOfRows(entityId)<5)
            return true;
        return false;
    }


    public static MostReTweetedTweets getModelObject(Status tweetInfo,int entityId){
        long parentId;
        if (tweetInfo.getRetweetedStatus()!=null) {
            parentId = tweetInfo.getRetweetedStatus().getId();
        }
        else
        {
            parentId = tweetInfo.getId();
        }
        return new MostReTweetedTweets(tweetInfo.getId(),entityId,tweetInfo.getText(), getReTweetCount(tweetInfo),parentId);
    }

    public static Long getReTweetCount(Status tweetInfo) {
        Long reTweetCount;
        if (tweetInfo.getRetweetedStatus() != null)
            reTweetCount = (long)tweetInfo.getRetweetedStatus().getRetweetCount();
        else
            reTweetCount = (long)tweetInfo.getRetweetCount();
        return reTweetCount;
    }

    public static void updateDatabase(int entityId, Status tweetInfo){
        MostReTweetedTweets minTweetCount = RepositoryMostReTweetedTweets.getMinTweetCount(entityId);
        if(minTweetCount.getReTweetCount() < getReTweetCount(tweetInfo)) {
            RepositoryMostReTweetedTweets.insert(getModelObject(tweetInfo,entityId));
            RepositoryMostReTweetedTweets.delete(minTweetCount.getTweetId());
        }
    }
}
