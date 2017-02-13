package services;

import models.BrandName;
import models.Handle;
import play.Logger;
import twitter4j.*;
import utilities.TwitterCredentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shubham on 15/9/14.
 */
public class FetchTweets {

    static List<String> keywords=new ArrayList<>();

    public static String[] upDate(int id){
        List<Handle> handles=Handle.find.where().eq("id",id).findList();
        for (Handle handle:handles)
            keywords.add(handle.name);

        List<BrandName> brandNames=BrandName.find.where().eq("id",id).findList();
        for (BrandName brandname:brandNames)
            keywords.add(brandname.name);
        for (String a:keywords)
            System.out.println(a);
        return keywords.toArray(new String[keywords.size()]);

    }

    public static void getTweets(){

        Logger.info("Getting tweets...");
        TwitterStream twitterStream = new TwitterStreamFactory(
                new TwitterCredentials().getConfiguration("380714562-r9v9CtrHfM5PTwSe18Do5aXhKorFedw1H2EL9Dq5","IA60iklXLgBlgI6yjZpzoDwaZR31OlUnt7yzqUeyCJfUR").build()).getInstance();
        FilterQuery fq = new FilterQuery();

        keywords.add("iphone");
        keywords.add("apple");
        fq.track(keywords.toArray(new String[keywords.size()]));
        Logger.info("keyword size="+keywords.size());

        AnalyticsOfSentimentScore.createSentimentScoreOfTweetInstance();

        StatusListener listener = new StatusListener() {

            int entityId = 1;//TODO


            public void onStatus(Status tweetInfo) {
                Logger.info("Recieved tweet with id "+tweetInfo.getId());
                TopTrendingHashTags.checkDataBaseToUpdate(entityId,tweetInfo);
                /*System.out.println("User Location " + tweetInfo.getUser().getLocation());*/

                Long StartingTime=new Date().getTime();
                TopFiveMostReTweedPosts.update(entityId, tweetInfo);

                try {

                    AnalyticsOfSentimentScore.checkDataBaseToUpdate(tweetInfo,entityId);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    AnalyticsOfImpressions.checkDataBaseToUpdate(tweetInfo,entityId);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


                GeoLocationService.updateDatabase(tweetInfo,entityId);

               MentionsService.upDateDataBase(entityId, tweetInfo);
//               System.out.println("Time  taken for analysis**********************************" + (new Date().getTime()- StartingTime));
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

        twitterStream.addListener(listener);
        twitterStream.filter(fq);
    }

}
