package repositories;

import com.avaje.ebean.Ebean;
import models.ResultJsonObjects.JsonIndividualSentiment;
import models.ResultJsonObjects.JsonSentimentScore;
import models.SentimentScore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubham on 16/9/14.
 */
public class SentimentScoreRepository {
    public static SentimentScore getLargestTimestamp(int entityId){
        if(getAllSentiment(entityId).size()!=0)
            return getAllSentiment(entityId).get(0);
        else
            return null;

    }

    public static void insert(SentimentScore SentimentScore){
        Ebean.save(SentimentScore);
    }

    public static List<SentimentScore> getAllSentiment(int entityId){
        return SentimentScore.find.where().eq("entityId",entityId).order().desc("timestamp").findList();

    }

    public static void upDateRepository(int entityId, int sentiment[]){
        SentimentScore SentimentScore = getAllSentiment(entityId).get(0);
        SentimentScore.setNumberOfNegativeTweets(SentimentScore.getNumberOfNegativeTweets() + sentiment[1]);
        SentimentScore.setNumberOfPositiveTweets(SentimentScore.getNumberOfPositiveTweets()+sentiment[0]);
        SentimentScore.update();
    }

    public static List<JsonSentimentScore> getSentimentScore(int entityId) {
        List<JsonSentimentScore> sentimentScores=new ArrayList<>();
        for(SentimentScore sentimentScore:getAllSentiment(entityId)) {
            sentimentScores.add(new JsonSentimentScore(sentimentScore.getTimestamp(),sentimentScore.getNumberOfPositiveTweets(),sentimentScore.getNumberOfNegativeTweets()));
        }
        return sentimentScores;
    }

    public static List<JsonIndividualSentiment> getPositiveSentimentScore(int entityId) {
        List<JsonIndividualSentiment> sentimentScores=new ArrayList<>();
        for(SentimentScore sentimentScore:getAllSentiment(entityId)) {
            sentimentScores.add(new JsonIndividualSentiment(sentimentScore.getTimestamp(),sentimentScore.getNumberOfPositiveTweets()));
        }
        return sentimentScores;
    }

    public static List<JsonIndividualSentiment> getNegativeSentimentScore(int entityId) {
        List<JsonIndividualSentiment> sentimentScores=new ArrayList<>();
        for(SentimentScore sentimentScore:getAllSentiment(entityId)) {
            sentimentScores.add(new JsonIndividualSentiment(sentimentScore.getTimestamp(),sentimentScore.getNumberOfNegativeTweets()));
        }
        return sentimentScores;
    }

    /*public static List<JsonPositiveSentiment> getAllPositiveSentiment(int entityId){
        List<JsonPositiveSentiment> positiveSentiments=new ArrayList<>();
        for(SentimentScore SentimentScore :getAllSentiment(entityId)){
            positiveSentiments.add(new JsonPositiveSentiment(SentimentScore.getTimestamp(), SentimentScore.getNumberOfPositiveTweets()));
        }
        return positiveSentiments;

    }

    public static List<JsonNegativeSentiMent> getAllNegativeSentiment(int entityId){
        List<JsonNegativeSentiMent> negativeSentiments=new ArrayList<>();
        for(SentimentScore SentimentScore :getAllSentiment(entityId)){
            negativeSentiments.add(new JsonNegativeSentiMent(SentimentScore.getTimestamp(), SentimentScore.getNumberOfNegativeTweets()));
        }
        return negativeSentiments;
    }*/

}
