package services;

import play.Logger;
import utilities.RabbitMqServer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import twitter4j.Status;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;

/**
 * Created by satheesh on 15/9/14.
 */
public class TweetConsumer {
    private final static String QUEUE_NAME = "twitterdata";

    public static void consumeTweets()
            throws java.io.IOException,
            InterruptedException, ClassNotFoundException {
        ByteArrayInputStream tweetBody;
        ObjectInputStream o;
        Status tweetInfo;
        Channel channel= RabbitMqServer.getChannelInstance();
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);

        int entityId=1;
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            tweetBody= new ByteArrayInputStream(delivery.getBody());
            o = new ObjectInputStream(tweetBody);
            tweetInfo = (Status) o.readObject();
            System.out.println(" [x] Received '" + +tweetInfo.getId());
            Logger.info("Recieved tweet with id " + tweetInfo.getId());
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
        }
    }
}
