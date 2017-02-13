package services;

import com.rabbitmq.client.Channel;
import models.*;
import models.Query;
import models.User;
import play.Logger;
import repositories.BrandNameRepository;
import repositories.EntityRepository;
import repositories.HandleRepository;
import repositories.UserEntitiesRepository;
import twitter4j.*;
import utilities.RabbitMqServer;
import utilities.TwitterCredentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by shubham on 17/9/14.
 */
public class UserRunnable implements Runnable {
    String screenName;

    String accessToken;

    String accessSecret;
    static List<Integer> entityIdList;
    static Query query;
    public static void checkBrandNames(String word){
        //Query query;
        List<BrandName> brandNames =BrandNameRepository.getIds(word);
        for (BrandName brandName : brandNames) {
            query=brandName.getQuery();
            if (entityIdList.contains(query) == false)
                entityIdList.add(query.getId());
        }

    }

    public static void checkHandles(String word)
    {


        List<Handle> handles = HandleRepository.getIds(word);
        for (Handle handle : handles) {
            query=handle.getQuery();
            if (entityIdList.contains(query) == false)
                entityIdList.add(query.getId());
        }
    }
    public List<Integer> getEntityId(Status tweetInfo){

        entityIdList=new ArrayList<>();
        StringTokenizer wordsInTweet = new StringTokenizer(tweetInfo.getText()," #@,");
        String word;
        while(wordsInTweet.hasMoreTokens())
        {
            word=wordsInTweet.nextToken();
            checkBrandNames(word);
            checkHandles(word);

        }
        return entityIdList;
    }
    public String getScreenName(){
        return this.screenName;
    }
    public UserRunnable(User user){
        this.accessSecret=user.getAccessSecret();
        this.accessToken=user.getAccessToken();
        this.screenName=user.getScreenName();
    }

    public void doAnalytics(Status tweetInfo){
        System.out.println("Got a tweet!!!");
        List<Integer> entityIdList= getEntityId(tweetInfo);

        for (Integer entityId : entityIdList) {
            System.out.println("analytics started");
            try {
                AnalyticsOfSentimentScore.checkDataBaseToUpdate(tweetInfo,entityId);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            TopFiveMostReTweedPosts.update(entityId, tweetInfo);
            InfluentialFollowerService.upDateDataBase(entityId,tweetInfo);
            //StartAnalytics.doAnalytics(tweetInfo);
            TopTrendingHashTags.checkDataBaseToUpdate(entityId,tweetInfo);
            try {
                AnalyticsOfImpressions.checkDataBaseToUpdate(tweetInfo,entityId);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            MentionsService.upDateDataBase(entityId,tweetInfo);
            GeoLocationService.updateDatabase(tweetInfo,entityId);

        }

    }
    public void getTweets(List<String> keywords){
        Logger.info("Getting tweets...");
        try {
            final Channel channel= RabbitMqServer.getChannelInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        TwitterCredentials twitterCredentials=new TwitterCredentials()
        TwitterStream twitterStream = new TwitterStreamFactory(
                new TwitterCredentials().getConfiguration(this.accessToken,this.accessSecret).build()).getInstance();
        FilterQuery fq = new FilterQuery();

        Logger.info("Listening for tweets for user "+this.getScreenName()+" with access token="+this.accessToken+" and access secret="+this.accessSecret);
        fq.track(keywords.toArray(new String[keywords.size()]));
        Logger.info("keyword size="+keywords.size());

        AnalyticsOfSentimentScore.createSentimentScoreOfTweetInstance();

        Logger.info("keyword size="+keywords.size());
        StatusListener listener = new StatusListener() {

//            int entityId = 1;//TODO



            public void onStatus(Status tweetInfo) {

//                System.out.println("sdlfjsdlfjl");
//                Logger.info("Got tweet with id = "+tweetInfo.getId());
                doAnalytics(tweetInfo);
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {

            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }


        };

        System.out.println(keywords);
        twitterStream.addListener(listener);
        twitterStream.filter(fq);
    }

    @Override
    public void run() {
        //fill the lists based on the user id

        List<Query> queries= UserEntitiesRepository.getEntities(this.getScreenName());
        List<BrandName> brandNames=new ArrayList<>();
        List<Handle> handles=new ArrayList<>();
        for(Query query:queries){
            brandNames.addAll(BrandNameRepository.getBrandNames(query.id));
            handles.addAll(HandleRepository.getHandles(query.id));
        }

        List<String> keywords=new ArrayList<>();
        for(BrandName brandName:brandNames) keywords.add(brandName.name);
        for(Handle handle:handles) keywords.add(handle.name);

        if (keywords.isEmpty()) Thread.currentThread().interrupt();

        getTweets(keywords);

        //construct keyword array from the above 3 lists

        //open stream api connection using the constructed keyword array and the user access tokens and secret
    }
}
