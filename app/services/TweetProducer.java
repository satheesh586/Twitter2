package services;

/**
 * Created by satheesh on 15/9/14.
 */

import utilities.RabbitMqServer;
import utilities.TwitterCredentials;
import com.rabbitmq.client.Channel;
import models.BrandName;
import models.Handle;
import twitter4j.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class TweetProducer {

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

    public static void getTweets() throws IOException {

        final Channel channel= RabbitMqServer.getChannelInstance();
        TwitterStream twitterStream = new TwitterStreamFactory(
                new TwitterCredentials().getConfiguration("Satheesh","Dannuri").build()).getInstance();
        FilterQuery fq = new FilterQuery();

//        String keyword[]={"#iphone6"};

        fq.track(keywords.toArray(new String[keywords.size()]));



        StatusListener listener = new StatusListener() {


            public void onStatus(Status tweetInfo)
            {

                System.out.println("@" + tweetInfo.getId() + " ");

                ByteArrayOutputStream tweet = new ByteArrayOutputStream();

                try {
                    ObjectOutputStream o = new ObjectOutputStream(tweet);
                    o.writeObject(tweetInfo);


                    channel.basicPublish("", "twitterdata", null,tweet.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }

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
