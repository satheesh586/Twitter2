package services;

import models.SentimentScore;
import repositories.SentimentScoreRepository;
import twitter4j.Status;
import utilities.SentiMentScoreOfTweet;

import java.io.IOException;

/**
 * Created by shubham on 16/9/14.
 */
public class AnalyticsOfSentimentScore {
    public static void checkDataBaseToUpdate(Status tweetInfo,int entityId) throws IOException, ClassNotFoundException {
        SentimentScore sentimentScore = SentimentScoreRepository.getLargestTimestamp(entityId);
        if(sentimentScore !=null && tweetInfo.getCreatedAt().getTime()- sentimentScore.getTimestamp().getTime()<60000){
            SentimentScoreRepository.upDateRepository(entityId, getSentiment(tweetInfo.getText()));
        }
        else {
            SentimentScoreRepository.insert(getModelObject(tweetInfo, entityId));
        }
    }

    public static SentiMentScoreOfTweet sentimentScoreOfTweet;
    public static void createSentimentScoreOfTweetInstance(){
        try {
            sentimentScoreOfTweet=new SentiMentScoreOfTweet();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static int[]  getSentiment(String tweetText) throws IOException, ClassNotFoundException {
        int[] sentiment=new int[2];
        double tweetScore=sentimentScoreOfTweet.getScoreOfSentence(tweetText);
        if (tweetScore>0.5)
            sentiment[0]=1;
        else if (tweetScore<-0.5)
            sentiment[1]=1;
        return sentiment;
    }

    public static SentimentScore getModelObject(Status tweetInfo,int entityId) throws IOException, ClassNotFoundException {
        int sentiment[]=getSentiment(tweetInfo.getText());
        java.sql.Timestamp timestamp =new java.sql.Timestamp(tweetInfo.getCreatedAt().getTime()-tweetInfo.getCreatedAt().getTime()%60000);
        return new SentimentScore(entityId,sentiment[0],sentiment[1], timestamp);
    }
}
